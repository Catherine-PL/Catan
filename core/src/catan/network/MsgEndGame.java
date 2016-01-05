package catan.network;

class MsgEndGame extends UpdateMessage {

	MsgEndGame() {
		super(UpdateType.END_GAME);
	}

}
