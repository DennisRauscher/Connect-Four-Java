package de.dennisr.game;

public class LobbySettings {

	private boolean localMultiplayer = true;
	private boolean timeMatch = false;
	private int timePerRound = 10;
	
	public LobbySettings(boolean localMultiplayer, boolean timeMatch){
		this.localMultiplayer = localMultiplayer;
		this.timeMatch = timeMatch;
	}

	public boolean isLocalMultiplayer() {
		return localMultiplayer;
	}

	public void setLocalMultiplayer(boolean localMultiplayer) {
		this.localMultiplayer = localMultiplayer;
	}

	public boolean isTimeMatch() {
		return timeMatch;
	}

	public void setTimeMatch(boolean timeMatch) {
		this.timeMatch = timeMatch;
	}

	public int getTimePerRound() {
		return timePerRound;
	}

	public void setTimePerRound(int timePerRound) {
		this.timePerRound = timePerRound;
	}
}
