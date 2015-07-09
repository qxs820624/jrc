package com.app.chatroom.sp;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.app.chatroom.contants.ConstantsJrc;

public class SystemSettingUtilSp {
	SharedPreferences sp;
	Editor editor;

	public SystemSettingUtilSp(SharedPreferences sp) {
		this.sp = sp;
		editor = sp.edit();
	}

	public void setUid(String uid) {
		editor.putString(ConstantsJrc.UID, uid);
		editor.commit();
	}

	public String getUid() {
		return sp.getString(ConstantsJrc.UID, "");
	}
	
	public void setJcl(String jcl) {
		editor.putString(ConstantsJrc.UID, jcl);
		editor.commit();
	}

	public String getJcl() {
		return sp.getString("jcl", "");
	}
	
	public void setJc(int jc) {
		editor.putInt("jc", jc);
		editor.commit();
	}

	public int getJc() {
		return sp.getInt("jc", 0);
	}
	
	public void setJct(int jct) {
		editor.putInt("jct", jct);
		editor.commit();
	}

	public int getJct() {
		return sp.getInt("jct",0);
	}

	public void setNick(String nick) {
		editor.putString(ConstantsJrc.NICK, nick);
		editor.commit();
	}

	public String getNick() {
		return sp.getString(ConstantsJrc.NICK, "");
	}

	public void setHeader(String header) {
		editor.putString(ConstantsJrc.HEADER, header);
		editor.commit();
	}

	public String getHeader() {
		return sp.getString(ConstantsJrc.HEADER, "");
	}

	public void setSign(String sign) {
		editor.putString(ConstantsJrc.SIGN, sign);
		editor.commit();
	}

	public String getSign() {
		return sp.getString(ConstantsJrc.SIGN, "");
	}

	public void setScore(String score) {
		editor.putString(ConstantsJrc.SCORE, score);
		editor.commit();
	}

	public String getScore() {
		return sp.getString(ConstantsJrc.SCORE, "");
	}

	public void setLevel(String level) {
		editor.putString(ConstantsJrc.LEVEL, level);
		editor.commit();
	}

	public String getLevel() {
		return sp.getString(ConstantsJrc.LEVEL, "");
	}

	public void setBirthday(String birthday) {
		editor.putString(ConstantsJrc.BIRTHDAY, birthday);
		editor.commit();
	}

	public String getBirthday() {
		return sp.getString(ConstantsJrc.BIRTHDAY, "");
	}

	public void setCity(String city) {
		editor.putString(ConstantsJrc.CITY, city);
		editor.commit();
	}

	public String getCity() {
		return sp.getString(ConstantsJrc.CITY, "");
	}

	public void setQQ(String qq) {
		editor.putString(ConstantsJrc.QQ, qq);
		editor.commit();
	}

	public String getQQ() {
		return sp.getString(ConstantsJrc.QQ, "");
	}

	public void setWeixin(String weixin) {
		editor.putString(ConstantsJrc.WEIXIN, weixin);
		editor.commit();
	}

	public String getWeixin() {
		return sp.getString(ConstantsJrc.WEIXIN, "");
	}

	public void setSex(String sex) {
		editor.putString(ConstantsJrc.SEX, sex);
		editor.commit();
	}

	public String getSex() {
		return sp.getString(ConstantsJrc.SEX, "");
	}

	public void setMailCount(int mailcount) {
		editor.putInt(ConstantsJrc.MAILCOUNT, mailcount);
		editor.commit();
	}

	public int getMailCount() {
		return sp.getInt(ConstantsJrc.MAILCOUNT, 0);
	}

	public void setPhone(String phone) {
		editor.putString(ConstantsJrc.PHONE, phone);
		editor.commit();
	}

	public String getPhone() {
		return sp.getString(ConstantsJrc.PHONE, "");
	}

	public void setEmail(String email) {
		editor.putString(ConstantsJrc.EMAIL, email);
		editor.commit();
	}

	public String getEmail() {
		return sp.getString(ConstantsJrc.EMAIL, "");
	}

	public void setOnlinePd(String pd) {
		editor.putString(ConstantsJrc.ONLINEPD, pd);
		editor.commit();
	}

	public String getOnlinePd() {
		return sp.getString(ConstantsJrc.ONLINEPD, "");
	}

	public void setVillagePd(String pd) {
		editor.putString(ConstantsJrc.VILLAGEPD, pd);
		editor.commit();
	}

	public String getVillagePd() {
		return sp.getString(ConstantsJrc.VILLAGEPD, "");
	}

	public void setVillagePeoplePd(String pd) {
		editor.putString(ConstantsJrc.VILLAGEPEOPLEPD, pd);
		editor.commit();
	}

	public String getVillagePeoplePd() {
		return sp.getString(ConstantsJrc.VILLAGEPEOPLEPD, "");
	}

	public void setVillageScorePd(String pd) {
		editor.putString(ConstantsJrc.VILLAGESOCREPD, pd);
		editor.commit();
	}

	public String getVillageScorePd() {
		return sp.getString(ConstantsJrc.VILLAGESOCREPD, "");
	}

	public void setMailPd(String pd) {
		editor.putString(ConstantsJrc.MAILPD, pd);
		editor.commit();
	}

	public String getMailPd() {
		return sp.getString(ConstantsJrc.MAILPD, "");
	}

	public void setMainPd(String pd) {
		editor.putString(ConstantsJrc.MAINPD, pd);
		editor.commit();
	}

	public String getMainPd() {
		return sp.getString(ConstantsJrc.MAINPD, "");
	}

	public void setMsgPid(String pid) {
		editor.putString(ConstantsJrc.MSGPID, pid);
		editor.commit();
	}

	public String getMsgPid() {
		return sp.getString(ConstantsJrc.MSGPID, "");
	}

	public void setBTime(String time) {
		editor.putString(ConstantsJrc.BTIME, time);
		editor.commit();
	}

	public String getBTime() {
		return sp.getString(ConstantsJrc.BTIME, "");
	}

	public void setOldPicPath(String path) {
		editor.putString(ConstantsJrc.OLDPIC_PATH, path);
		editor.commit();
	}

	public String getOldPicPath() {
		return sp.getString(ConstantsJrc.OLDPIC_PATH, "");
	}

	public void setNewPicPath(String path) {
		editor.putString(ConstantsJrc.NEWPIC_PATH, path);
		editor.commit();
	}

	public String getNewPicPath() {
		return sp.getString(ConstantsJrc.NEWPIC_PATH, "");
	}

	public void setAudioPath(String path) {
		editor.putString(ConstantsJrc.AUDIO_MY_PATH, path);
		editor.commit();
	}

	public String getAudioPath() {
		return sp.getString(ConstantsJrc.AUDIO_MY_PATH, "");
	}

	public void setAudioLength(String length) {
		editor.putString(ConstantsJrc.AUDIO_LENGTH, length);
		editor.commit();
	}

	public String getAudioLength() {
		return sp.getString(ConstantsJrc.AUDIO_LENGTH, "");
	}

	public void setAudioModel(String model) {
		editor.putString(ConstantsJrc.AUDIOMODEL, model);
		editor.commit();
	}

	public String getAudioModel() {
		return sp.getString(ConstantsJrc.AUDIOMODEL, "");
	}

	public void setAudioAuto(String auto) {
		editor.putString(ConstantsJrc.AUDIOAUTO, auto);
		editor.commit();
	}

	public String getAudioAuto() {
		return sp.getString(ConstantsJrc.AUDIOAUTO, "");
	}

	public void setPicAuto(String auto) {
		editor.putString(ConstantsJrc.PICAUTO, auto);
		editor.commit();
	}

	public String getPicAuto() {
		return sp.getString(ConstantsJrc.PICAUTO, "");
	}

	public void setAudioFlag(String flag) {
		editor.putString(ConstantsJrc.AUDIOFLAG, flag);
		editor.commit();
	}

	public String getAudioFlag() {
		return sp.getString(ConstantsJrc.AUDIOFLAG, "");
	}

	public void setMd(String md) {
		editor.putString(ConstantsJrc.MD, md);
		editor.commit();
	}

	public String getMd() {
		return sp.getString(ConstantsJrc.MD, "");
	}

	public void setMg(String mg) {
		editor.putString(ConstantsJrc.MG, mg);
		editor.commit();
	}

	public String getMg() {
		return sp.getString(ConstantsJrc.MG, "");
	}

	public void setMs(String ms) {
		editor.putString(ConstantsJrc.MS, ms);
		editor.commit();
	}

	public String getMs() {
		return sp.getString(ConstantsJrc.MS, "");
	}

	public void setTcount(String tcount) {
		editor.putString(ConstantsJrc.TCOUNT, tcount);
		editor.commit();
	}

	public String getTcount() {
		return sp.getString(ConstantsJrc.TCOUNT, "");
	}

	public void setToken(String token) {
		editor.putString(ConstantsJrc.TOKEN, token);
		editor.commit();
	}

	public String getToken() {
		return sp.getString(ConstantsJrc.TOKEN, "");
	}
	public void setFbg(String fbg) {
		editor.putString(ConstantsJrc.fbg, fbg);
		editor.commit();
	}

	public String getFbg() {
		return sp.getString(ConstantsJrc.fbg, "");
	}
	
	public void setFfg(String ffg) {
		editor.putString(ConstantsJrc.ffg, ffg);
		editor.commit();
	}

	public String getFfg() {
		return sp.getString(ConstantsJrc.ffg, "");
	}


	public String getHelp() {
		return sp.getString(ConstantsJrc.help, "");
	}
	public void setHelp(String help) {
		editor.putString(ConstantsJrc.help, help);
		editor.commit();
	}

	public String getActivity() {
		return sp.getString(ConstantsJrc.activity, "");
	}
	public void setActivity(String activity) {
		editor.putString(ConstantsJrc.activity, activity);
		editor.commit();
	}

	public String getShop() {
		return sp.getString(ConstantsJrc.shop, "");
	}
	public void setShop(String shop) {
		editor.putString(ConstantsJrc.shop, shop);
		editor.commit();
	}
	public String getLoading() {
		return sp.getString("loading", "");
	}
	public void setLoading(String loading) {
		editor.putString("loading", loading);
		editor.commit();
	}
}
