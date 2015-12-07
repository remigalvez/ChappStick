package com.remigalvez.chappstick.objects;

import android.os.Parcel;
import android.os.Parcelable;

public class ChatMessage implements Parcelable {
    private long id;
    private boolean isMe;
    private String message;
    private Long userId;
    private String dateTime;

    public ChatMessage() { }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public boolean getIsme() {
        return isMe;
    }
    public void setMe(boolean isMe) {
        this.isMe = isMe;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public long getUserId() { return userId; }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getDate() {
        return dateTime;
    }

    public void setDate(String dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeValue(isMe);
        dest.writeString(message);
        dest.writeLong(userId != null ? userId : 0L);
        dest.writeString(dateTime);
    }

    protected ChatMessage(Parcel in) {
        id = in.readLong();
        message = in.readString();
        dateTime = in.readString();
    }

    public static final Creator<ChatMessage> CREATOR = new Creator<ChatMessage>() {
        @Override
        public ChatMessage createFromParcel(Parcel in) {
            return new ChatMessage(in);
        }

        @Override
        public ChatMessage[] newArray(int size) {
            return new ChatMessage[size];
        }
    };
}
