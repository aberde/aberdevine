package kr.co.gitech.storyz.common.push;

/**
 * 
 */
public class PushMessage {

	public PushMessageType type;

	public long club_seq;

	public boolean useBadge;

	public PushMessageType getType() {
		return type;
	}

	public void setType(PushMessageType type) {
		this.type = type;
	}

	public long getClub_seq() {
		return club_seq;
	}

	public void setClub_seq(long club_seq) {
		this.club_seq = club_seq;
	}

	public boolean isUseBadge() {
		return useBadge;
	}

	public void setUseBadge(boolean useBadge) {
		this.useBadge = useBadge;
	}
}
