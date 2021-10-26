package com.guritchistudios.buddify;

public class CommentsModel {
    public CommentsModel() {
    }

    String cId;
    String comment;
    String ptime;
    String udp;
    String uid;
    String uemail;
    String uname;

    public CommentsModel(String cId, String comment, String ptime, String udp, String uid, String uemail, String uname) {
        this.cId = cId;
        this.comment = comment;
        this.ptime = ptime;
        this.udp = udp;
        this.uid = uid;
        this.uemail = uemail;
        this.uname = uname;
    }


}
