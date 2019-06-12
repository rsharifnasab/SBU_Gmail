package UIUX.Controllers;

import BasicClasses.Cord;
import ChessGame.Board;
import ChessGame.ChizaayeMohrehaa.Mohre;
import ClientAndHandlerCommunication.Commands.RecieveChatCommand;
import ClientAndHandlerCommunication.Commands.SendChatCommand;
import ClientAndHandlerCommunication.Commands.MadeAMoveCommand;
import ClientAndHandlerCommunication.Responses.JoinedGameResponse;
import Enums.ChatChannelType;
import Enums.Color;
import Enums.JoinerType;
import Game.Match;
import Game.Profile;
import NetworkStuff.ClientSide.Client;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameRoomController extends ParentController implements Initializable  {
	@FXML
	TextField chatInput;
	@FXML
	Label hostLabel;
	@FXML
	Label guestLabel;
	@FXML
	VBox audienceBox;

	@FXML
	RadioButton rivalSelect;
	@FXML
	RadioButton audienceSelect;
	@FXML
	VBox audienceChatBox;
	@FXML
	VBox rivalChatBox;
	@FXML
	ScrollPane aPane;
	@FXML
	ScrollPane rPane;
	@FXML
	GridPane boardPane;

	@FXML
	VBox hostMoves;
	@FXML
	VBox guestMoves;

	private Match match;
	private Profile host;
	private Profile guest;

	private Cord selectedPieceToMoveCord = null;
	private ArrayList < Cord > selectedPieceValidDests = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ToggleGroup chatRadio = new ToggleGroup();
		rivalSelect.setToggleGroup(chatRadio);
		audienceSelect.setToggleGroup(chatRadio);
		rivalSelect.setSelected(true);
		this.match = Client.getProfile().getActiveMatch();
		this.host = match.getHostProfile();
		this.hostLabel.setText(host.getUserName());
		this.updateGridPane( this.match.getBoard() );
		if (match.getGuestProfile() != null) {

			//if you are the host and guest has already joined
		   /* if (match.getHostProfile().equals(Client.getProfile())) {
				this.guest = match.getGuestProfile();
				this.guestLabel.setText(guest.getUserName());

			}


			//if you are the guest
			else if (Client.getProfile().equals(match.getGuestProfile())) {

				this.guest = match.getGuestProfile();
				this.guestLabel.setText(guest.getUserName());

			}

			//if you are audience and guest has joined already
			else  {*/
			this.guest = match.getGuestProfile();
			this.guestLabel.setText(guest.getUserName());
			// }

			if ( match.getHostColor() == Color.BLACK )
				match.setCurrentPlayer( match.getGuestProfile() );

		}
	   /*
		//if you are the host and quest hasnt joined yet
		else if (match.getHostProfile().equals(Client.getProfile())) {


		}


		//if you are audience and guest hasnt joined yet
		else {

		}*/


		for (Profile audience : match.getAudience()) {

			if (!Client.getProfile().equals(audience))
				audienceBox.getChildren().add(audience.getProfileTile());
			else {
				audienceSelect.setSelected(true);
				rivalSelect.setDisable(true);
				rPane.setVisible(false);
				aPane.setVisible(true);
			}

		}

		boardPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				System.out.println( "PROFILES:" + Client.getProfile() + " " + match.getCurrentPlayer() );
				if (Client.getProfile().equals(match.getCurrentPlayer())) {
					int x = ((int) event.getX()) / 80, y = ((int) event.getY()) / 80;
					if (match.getBoard().getBlocks()[y][x].getMohre() != null
							&& match.getBoard().getBlocks()[y][x].getMohre().getColor() == match.getCurrentColor()) {
						System.out.println("I am in this if ");
						selectedPieceToMoveCord = new Cord(x, y);
						selectedPieceValidDests = match.getBoard().getBlocks()[y][x].getMohre().getValidDests(match.getBoard().getCopy(), true);
						for ( Cord temp : selectedPieceValidDests )
							match.getBoard().getBlocks()[temp.getY()][temp.getX()].setSelected( true );
						updateGridPane( match.getBoard() );
					} else {
						if (selectedPieceToMoveCord != null)
							if (selectedPieceValidDests.contains(new Cord(x, y))) {
								match.getBoard().getBlocks()[y][x].setMohre( match.getBoard().getBlocks()[selectedPieceToMoveCord.getY()][selectedPieceToMoveCord.getX()].getMohre() );
								match.getBoard().getBlocks()[y][x].getMohre().setCord( new Cord( x, y ) );
								match.getBoard().getBlocks()[selectedPieceToMoveCord.getY()][selectedPieceToMoveCord.getX()].setMohre( null );
								updateGridPane( match.getBoard() );
								match.changeCurrentColor();
								match.changeCurrentPlayer();
								try {
									Client.gameOut.writeObject( new MadeAMoveCommand( match, Client.getProfile(),
											new Cord( selectedPieceToMoveCord.getX(), selectedPieceToMoveCord.getY() ),
											new Cord( x, y ) ) );
								} catch (IOException e) {
									e.printStackTrace();
								}
								selectedPieceToMoveCord = null;
								selectedPieceValidDests = null;
							}
					}
				}
			}
		});


		Thread waiter = new Thread(waitForPlayers());
		waiter.start();

		Thread chatReciever = new Thread(waitForChat());
		chatReciever.start();

		Thread movesWaiter = new Thread( this.waitForChessMoves() );
		movesWaiter.start();

	}

	public void sendMsg() {

		if (rivalSelect.isSelected()) {
			this.sendChatCommand(new SendChatCommand(chatInput.getText(),match,ChatChannelType.RIVAL_CHANNEL,Client.getProfile().getUserName()));
			chatInput.clear();
		}
		else {

			this.sendChatCommand(new SendChatCommand(chatInput.getText(),match,ChatChannelType.AUDIENCES_CHANNEL,Client.getProfile().getUserName()));
			chatInput.clear();
		}


	}

	public void exitRoom() {


	}

	private Runnable waitForChessMoves() {

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				synchronized (Client.gameIn) {
					while (true) {
						try {
							MadeAMoveCommand command = (MadeAMoveCommand) Client.gameIn.readObject();
//						match.setBoard( command.getMatch().getBoard() );
							match = command.getMatch();
							if (!command.getMoveOwner().equals(Client.getProfile())) {    //Age yeki Dge harekat ro anjaam dade bood
								if (match.getHostProfile().equals(Client.getProfile()) || match.getHostProfile().equals(Client.getProfile())) {
									if (match.getCurrentColor() == Color.BLACK) {
										Mohre shaah = match.getBoard().getShaah(Color.BLACK);
										if (shaah.getHarifValidCords(match.getBoard()).contains(shaah.getCord()))
											System.out.println("BAAAKHTTIIIII");
									}
									if (match.getCurrentColor() == Color.WHITE) {
										Mohre shaah = match.getBoard().getShaah(Color.WHITE);
										if (shaah.getHarifValidCords(match.getBoard()).contains(shaah.getCord()))
											System.out.println("BAAAKHTIIII");
									}
								}
							}
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									System.out.println("Let's update the gridPane...");
									updateGridPane(command.getMatch().getBoard());
									if (command.getMoveOwner().equals(command.getMatch().getHostProfile()))
										hostMoves.getChildren().add(new Label(Integer.toString(command.getStart().getX())
												+ command.getStart().getY() + command.getEnd().getX()
												+ command.getEnd().getY()));
									if (command.getMoveOwner().equals(command.getMatch().getGuestProfile()))
										guestMoves.getChildren().add(new Label(Integer.toString(command.getStart().getX())
												+ command.getStart().getY() + command.getEnd().getX()
												+ command.getEnd().getY()));
								}
							});
						} catch (IOException | ClassNotFoundException e) {
							e.printStackTrace();
						}

					}
				}
			}


		};
		return runnable;

	}

	private Runnable waitForPlayers() {

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				while (true) {
					synchronized (Client.joinGameIn) {

						try {
							System.out.println("waiting for a player to join MATCH!");

							JoinedGameResponse response = (JoinedGameResponse) Client.joinGameIn.readObject();
							match = response.getMatch();
							if (response.getJoinerType() == JoinerType.GUEST) {
								System.out.println(response.getProfile() + " joined as guest");
								guest = response.getProfile();

								Platform.runLater(new Runnable() {
									@Override
									public void run() {
										guestLabel.setText(guest.getUserName());
									}
								});

							} else {
								System.out.println(response.getProfile() + " joined as audience");

								Platform.runLater(new Runnable() {
									@Override
									public void run() {
										audienceBox.getChildren().add(response.getProfile().getProfileTile());
									}
								});

							}

						} catch (IOException | ClassNotFoundException e) {
							e.printStackTrace();
						}


					}
				}
			}
		};
		return runnable;
	}

	private Runnable waitForChat() {

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				while (true) {
					synchronized (Client.chatIn) {
						try {
							System.out.println("Waiting for chat");
							RecieveChatCommand recieve=(RecieveChatCommand) Client.chatIn.readObject();
						   // HBox box = getChatTile(recieve.getMsg(), recieve.getSender());
							if (recieve.getChannelType()== ChatChannelType.AUDIENCES_CHANNEL) {

								Platform.runLater(new Runnable() {
									@Override
									public void run() {


										//chatBox.getChildren().addAll( new Label(recieve.getSender()+": "+recieve.getMsg()));
										audienceChatBox.getChildren().addAll(new Label(recieve.getSender()+": "+recieve.getMsg()));
									}
								});
							}
							else {

								Platform.runLater(new Runnable() {
									@Override
									public void run() {
										//System.out.println(box.getChildren());
									   // chatBox.getChildren().addAll(new Label(recieve.getSender()+": "+recieve.getMsg()));
										rivalChatBox.getChildren().addAll(new Label(recieve.getSender()+": "+recieve.getMsg()));
									}
								});



							}
						}catch (IOException|ClassNotFoundException e){
							e.printStackTrace();
						}

					}
				}
			}
		};
		return runnable;

	}

	private void updateGridPane( Board board ) {
//		ImageView test = new ImageView( new Image(Mohre.MOHRE_IMAGES_ADDRESS + board.getBlocks()[0][0].getMohre().toString() ) );
		for ( int i = 0; i < Board.SIZE * Board.SIZE; i++ ) {
			if ( board.getBlocks()[ i / Board.SIZE ][ i % Board.SIZE ].getMohre() != null ) {
				String imageAddress = Mohre.MOHRE_IMAGES_ADDRESS + board.getBlocks()[i/Board.SIZE][i%Board.SIZE].getMohre().toString();
				ImageView currentImage = (ImageView) boardPane.getChildren().get(i);
				currentImage.setImage( new Image( imageAddress ) );
/*				if ( board.getBlocks()[i/Board.SIZE][i%Board.SIZE].isSelected() ) {
					System.out.println( new Cord( i % Board.SIZE, i / Board.SIZE ) );
					currentImage.setStyle("-fx-border-width: 3;" + " -fx-border-color: black;");
					board.getBlocks()[i/Board.SIZE][i%Board.SIZE].setSelected( false );
				}*/
//				((ImageView) boardPane.getChildren().get(i)).setImage( new Image(imageAddress ) );
			}
			else {
				ImageView temp = (ImageView) boardPane.getChildren().get(i);
				temp.setImage( null );
				if ( board.getBlocks()[i/Board.SIZE][i%Board.SIZE].isSelected() ) {
//					temp.setStyle( "-fx-border-width: 3; -fx-border-color: black" );
					temp.setImage( new Image( "/Assets/Chess/chessselected.png" ) );
					board.getBlocks()[i/Board.SIZE][i%Board.SIZE].setSelected( false );
				}
			}
		}
	}


	private HBox getChatTile(String msg, String sender) {
		System.out.println(msg+sender);
		HBox chatTile = new HBox();

		Label senderLabel = new Label(sender + ": ");
		Label msgLabel = new Label(msg);
		HBox.setHgrow(msgLabel, Priority.ALWAYS);
		HBox.setHgrow(senderLabel, Priority.ALWAYS);
		senderLabel.setFont(new Font(15));
		msgLabel.setFont(new Font(15));
		//senderLabel.setStyle("-fx-color-label-visible: red;");
		//msgLabel.setStyle("-fx-color-label-visible: red;");
		chatTile.setStyle("-fx-border-style: solid inside ;" + "-fx-border-width: 2px;"+"-fx-border-color: black;");
		chatTile.setAlignment(Pos.CENTER_LEFT);
	   // chatTile.setSpacing(15);

		chatTile.getChildren().addAll(senderLabel, msgLabel);

		return chatTile;
	}

	public void loadRivalChat(){
		rPane.setVisible(true);

		aPane.setVisible(false);
	}
	public void loadAudienceChat(){

		aPane.setVisible(true);

		rPane.setVisible(false);
	}


}
