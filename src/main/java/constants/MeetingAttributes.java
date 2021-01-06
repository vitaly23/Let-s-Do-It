package constants;

public final class MeetingAttributes {
	
	public static final String MEETING_DATE_FORMAT = "hh:mm MM/dd/yyyy";
	
	public static final String NAME = "name";
	public static final String AVATAR = "avatar";
	public static final String MEETING_START_DATE = "meetingStartDate";
	public static final String MEETING_END_DATE = "meetingEndDate";
	public static final String NUMBER_OF_PARTICIPANTS = "numberOfParticipants";
	public static final String MAX_PARTICIPANTS_NUMBER = "maxParticipantsNumber";
	public static final String[] ALL_MEETING_ATTRIBUTES = { NAME, AVATAR, OperationAttributes.TYPE_OF_SPORT,
			MEETING_START_DATE, MEETING_END_DATE, OperationAttributes.LAT, OperationAttributes.LNG,
			NUMBER_OF_PARTICIPANTS, MAX_PARTICIPANTS_NUMBER };

}
