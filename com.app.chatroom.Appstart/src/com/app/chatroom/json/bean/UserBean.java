package com.app.chatroom.json.bean;

/**
 * 用户信息
 * 
 * @author J.King
 * 
 */
public class UserBean {
	private int ret; // 返回结果
	private String tip;// 提示信息
	private String pd;// pd
	private int uid;// 用户编号
	private String nick;// 用户昵称
	private String header;// 用户头像链接
	private String header_b;// 用户大头像链接
	private String sign;// 用户签名
	private String score;// 用户积分
	private String level;// 用户称号
	private String birthday;// 用户生日
	private String city;// 用户城市
	private int qq;// 用户QQ
	private String weixin;// 用户微信
	private String sex;// 用户性别
	private int mcount;// 邮件
	private int tcount;// 任务个数
	private String vname; // 版本名字
	private String vurl; // 版本URL
	private int vsize; // 版本大小
	private String token;// 身份令牌
	private int md;
	private int mg;
	private int ms;
	private int istj;// 推荐按钮
	private int isshop;// 商城按钮
	private int isf;// 是否被关注
	private int fc;// 关注好友
	private int fc1;// 被关注数
	private int gc;// 礼物数量
	private int pc;// 上传数量
	private int isb;// 是否拉黑
	private int dzc;// 大嘴数量
	private int xjc;// 小贱数量
	private int bbc;// 背包数量
	private String dj;// 道具路径
	private String help;// 首页帮助 路径
	private String shop;// 商城路径
	private String hd;// 活动路径
	private String jz="";
	private String fbg , ffg ;
	private String jzc = "" ;
	private int jzid ;
	private String jcl ;
	private int jc , jct;
	private String time ;
    private int bg ;
    private String tip1 ,tip2 ,tip3 ,tip4 ,tip5,tip0;
	public UserBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserBean(int ret, String tip, String pd, int uid, String nick,
			String header, String header_b, String sign, String score,
			String level, String birthday, String city, int qq, String weixin,
			String sex, int mcount, int tcount, String vname, String vurl,
			int vsize, String token, int md, int mg, int ms, int istj,
			int isshop, int isf, int fc, int fc1, int gc, int pc, int isb,
			int dzc, int xjc, int bbc, String dj, String help, String shop,
			String hd , String jz ,String fbg , String ffg , String jzc ,int jzid ,String jcl ,int jc, int jct , String time , int bg,String tip1,String tip2,String tip3,String tip4,String tip5,String tip0) {
		super();
		this.ret = ret;
		this.tip = tip;
		this.pd = pd;
		this.uid = uid;
		this.nick = nick;
		this.header = header;
		this.header_b = header_b;
		this.sign = sign;
		this.score = score;
		this.level = level;
		this.birthday = birthday;
		this.city = city;
		this.qq = qq;
		this.weixin = weixin;
		this.sex = sex;
		this.mcount = mcount;
		this.tcount = tcount;
		this.vname = vname;
		this.vurl = vurl;
		this.vsize = vsize;
		this.token = token;
		this.md = md;
		this.mg = mg;
		this.ms = ms;
		this.istj = istj;
		this.isshop = isshop;
		this.isf = isf;
		this.fc = fc;
		this.fc1 = fc1;
		this.gc = gc;
		this.pc = pc;
		this.isb = isb;
		this.dzc = dzc;
		this.xjc = xjc;
		this.bbc = bbc;
		this.dj = dj;
		this.help = help;
		this.shop = shop;
		this.hd = hd;
		this.jz = jz ;
		this.fbg = fbg;
		this.ffg = ffg ;
		this.jzc = jzc ;
		this.jzid = jzid ;
		this.jc = jc ;
		this.jct = jct ;
		this.jcl = jcl ;
		this.time = time ;
		this.bg = bg ;
		this.tip1 = tip1 ;
		this.tip2 = tip2 ;
		this.tip3 = tip3 ;
		this.tip4 = tip4 ;
		this.tip5 = tip5 ;
		this.tip0 = tip0;
	}

	
	public String getTip0() {
		return tip0;
	}

	public void setTip0(String tip0) {
		this.tip0 = tip0;
	}

	public String getTip5() {
		return tip5;
	}

	public void setTip5(String tip5) {
		this.tip5 = tip5;
	}

	public String getTip1() {
		return tip1;
	}

	public void setTip1(String tip1) {
		this.tip1 = tip1;
	}

	public String getTip2() {
		return tip2;
	}

	public void setTip2(String tip2) {
		this.tip2 = tip2;
	}

	public String getTip3() {
		return tip3;
	}

	public void setTip3(String tip3) {
		this.tip3 = tip3;
	}

	public String getTip4() {
		return tip4;
	}

	public void setTip4(String tip4) {
		this.tip4 = tip4;
	}

	public int getBg() {
		return bg;
	}

	public void setBg(int bg) {
		this.bg = bg;
	}

	public String getFbg() {
		return fbg;
	}

	public void setFbg(String fbg) {
		this.fbg = fbg;
	}

	public String getFfg() {
		return ffg;
	}

	public void setFfg(String ffg) {
		this.ffg = ffg;
	}

	public int getRet() {
		return ret;
	}

	public void setRet(int ret) {
		this.ret = ret;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getPd() {
		return pd;
	}

	public void setPd(String pd) {
		this.pd = pd;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getQq() {
		return qq;
	}

	public void setQq(int qq) {
		this.qq = qq;
	}

	public String getWeixin() {
		return weixin;
	}

	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getMcount() {
		return mcount;
	}

	public void setMcount(int mcount) {
		this.mcount = mcount;
	}

	public int getTcount() {
		return tcount;
	}

	public void setTcount(int tcount) {
		this.tcount = tcount;
	}

	public String getVname() {
		return vname;
	}

	public void setVname(String vname) {
		this.vname = vname;
	}

	public String getVurl() {
		return vurl;
	}

	public void setVurl(String vurl) {
		this.vurl = vurl;
	}

	public int getVsize() {
		return vsize;
	}

	public void setVsize(int vsize) {
		this.vsize = vsize;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getMd() {
		return md;
	}

	public void setMd(int md) {
		this.md = md;
	}

	public int getMg() {
		return mg;
	}

	public void setMg(int mg) {
		this.mg = mg;
	}

	public int getMs() {
		return ms;
	}

	public void setMs(int ms) {
		this.ms = ms;
	}

	public int getIstj() {
		return istj;
	}

	public void setIstj(int istj) {
		this.istj = istj;
	}

	public int getIsshop() {
		return isshop;
	}

	public void setIsshop(int isshop) {
		this.isshop = isshop;
	}

	public int getIsf() {
		return isf;
	}

	public void setIsf(int isf) {
		this.isf = isf;
	}

	public int getFc() {
		return fc;
	}

	public void setFc(int fc) {
		this.fc = fc;
	}

	public int getFc1() {
		return fc1;
	}

	public void setFc1(int fc1) {
		this.fc1 = fc1;
	}

	public int getGc() {
		return gc;
	}

	public void setGc(int gc) {
		this.gc = gc;
	}

	public int getPc() {
		return pc;
	}

	public void setPc(int pc) {
		this.pc = pc;
	}

	public String getHeader_b() {
		return header_b;
	}

	public void setHeader_b(String header_b) {
		this.header_b = header_b;
	}

	public int getIsb() {
		return isb;
	}

	public void setIsb(int isb) {
		this.isb = isb;
	}

	public int getDzc() {
		return dzc;
	}

	public void setDzc(int dzc) {
		this.dzc = dzc;
	}

	public int getXjc() {
		return xjc;
	}

	public void setXjc(int xjc) {
		this.xjc = xjc;
	}

	public int getBbc() {
		return bbc;
	}

	public void setBbc(int bbc) {
		this.bbc = bbc;
	}

	public String getDj() {
		return dj;
	}

	public void setDj(String dj) {
		this.dj = dj;
	}

	public String getHelp() {
		return help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	public String getShop() {
		return shop;
	}

	public void setShop(String shop) {
		this.shop = shop;
	}

	public String getHd() {
		return hd;
	}

	public void setHd(String hd) {
		this.hd = hd;
	}
	

	public String getJz() {
		return jz;
	}

	public void setJz(String jz) {
		this.jz = jz;
	}

	
	public String getJzc() {
		return jzc;
	}

	public void setJzc(String jzc) {
		this.jzc = jzc;
	}

	public int getJzid() {
		return jzid;
	}

	public void setJzid(int jzid) {
		this.jzid = jzid;
	}
	
	

	public String getJcl() {
		return jcl;
	}

	public void setJcl(String jcl) {
		this.jcl = jcl;
	}

	public int getJc() {
		return jc;
	}

	public void setJc(int jc) {
		this.jc = jc;
	}

	public int getJct() {
		return jct;
	}

	public void setJct(int jct) {
		this.jct = jct;
	}


	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String toString() {
		return "返回结果：" + this.ret + ",提示：" + this.tip + "PD:" + this.pd
				+ ",用户编号：" + this.uid + ",用户昵称：" + this.nick + ",用户头像："
				+ this.header + ",大头像：" + this.header_b + ",用户签名：" + this.sign
				+ ",用户积分：" + this.score + ",用户称号：" + this.level + ",生日："
				+ this.birthday + ",出生地：" + this.city + ",QQ:" + this.qq
				+ ",微信：" + this.weixin + ",性别：" + this.sex + ",消息条数："
				+ this.mcount + "任务个数：" + this.tcount + ",版本名称：" + this.vname
				+ ",版本大小：" + this.vsize + ",版本链接：" + this.vurl + ",钻石："
				+ this.md + ",金币：" + this.mg + ",银币：" + this.ms + ",推荐按钮："
				+ this.istj + ",是否关注：" + this.isf + ",关注好友：" + this.fc
				+ ",被关注：" + this.fc1 + ",收到礼物：" + this.gc + ",上传数：" + this.pc
				+ ",身份令牌：" + this.token + ",是否拉黑：" + this.isb + ",大嘴数量："
				+ this.dzc + ",小贱数量：" + this.xjc + "背包数量：" + this.bbc
				+ ",道具路径：" + this.dj + ",首页帮助路径：" + this.help + ",商店路径："
				+ this.shop + ",活动路径：" + this.hd + ",商店路径："
						+ this.jz+ ",jcl：" + this.jcl + ",jc："
								+ this.jc + ",jct："
										+ this.jct;
	}

}
