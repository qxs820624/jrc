package com.app.chatroom.contants;

import java.io.File;

import android.content.SharedPreferences;
import android.os.Environment;

import com.app.chatroom.sp.SystemSettingUtilSp;

public class ConstantsJrc {
	SharedPreferences sp;
	SystemSettingUtilSp su;
	public static final String LOADDATA = "http://jrc.hutudan.com:8080/jianrencun_admin/restofile_new.jsp";
	public static final String LOGIN = "http://jrc.hutudan.com/usercenter/login.php";
	public static final String CHECK = "http://duome.cn/client/check.php"; // 启动信息检测
	public static final String ENTERROOM = "http://jrc.hutudan.com/chat/enter.php";
	public static final String EXITROOM = "http://jrc.hutudan.com/chat/exit.php";
	public static final String ONLINEUSER = "http://jrc.hutudan.com/chat/users.php";
	public static final String SENDMSG = "http://jrc.hutudan.com/chat/sendmsg.php";
	public static final String SENDMAIL = "http://jrc.hutudan.com/usercenter/sendmsg.php";
	public static final String MSGURL = "http://jrc.hutudan.com/chat/msgs.php";
	public static final String EDITINFO = "http://jrc.hutudan.com/usercenter/edit.php";
	public static final String UPLOADPHOTO = "http://jrc.hutudan.com/usercenter/edit_header.php";
	public static final String ONLINEUSERINFO = "http://jrc.hutudan.com/usercenter/userinfo.php";
	public static final String CODE_URL = "http://jrc.hutudan.com/usercenter/logincode.php";
	public static final String CHOOSEID_URL = "http://jrc.hutudan.com/usercenter/relogin.php";
	public static final String VILLAGEURL = "http://jrc.hutudan.com/usercenter/cuninfo.php";
	public static final String VILLAGEPEOPLEURL = "http://jrc.hutudan.com/usercenter/from_gifts_top.php";
	public static final String VILLAGECAIFUURL = "http://jrc.hutudan.com/usercenter/cuntop.php";
	public static final String VILLAGESCORE = "http://jrc.hutudan.com/usercenter/get_gifts_top.php";
	public static final String MAILLISTURL = "http://jrc.hutudan.com/usercenter/msglist.php";
	public static final String GETBIGIMAGE = "http://jrc.hutudan.com/chat/getimage.php";
	public static final String MAINMORE = "http://jrc.hutudan.com/jrc/more.php";
	public static final String FOLLOW = "http://jrc.hutudan.com/usercenter/tofollow.php"; // 是否添加关注
	public static final String FOLLOWS = "http://jrc.hutudan.com/usercenter/follows.php"; // 关注、被关注列表
	public static final String FOLLOWMOVE = "http://jrc.hutudan.com/usercenter/move.php"; // 关注列表移动
	public static final String GIFTLIST = "http://jrc.hutudan.com/usercenter/giftlist.php";// 礼物列表
	public static final String GIFTTYPE = "http://jrc.hutudan.com/usercenter/giftcate.php";// 礼物分类
	public static final String TOGIFT = "http://jrc.hutudan.com/usercenter/togift.php";// 送礼接口
	public static final String GIFTS = "http://jrc.hutudan.com/usercenter/gifts.php";
	public static final String BLACK = "http://jrc.hutudan.com/usercenter/toban.php";// 是否拉黑
	public static final String BLACKS = "http://jrc.hutudan.com/usercenter/bans.php";// 拉黑列表
	public static final String ROOMS = "http://jrc.hutudan.com/chat/rooms.php";
	public static final String USERDZURL = "http://jrc.hutudan.com/music/list.php?flg=8";
	public static final String URLQD = "http://jrc.hutudan.com/usercenter/get_package.php"; //签到
	public static final String DYDETATIL = "http://jrc.hutudan.com/usercenter/related_item.php";
	/** 进度条状态 **/
	public static final int HANDLER_CANCEL_PROGRESS = 0;
	public static final int HANDLER_SHOW_PROGRESS = 1;
	public static final int HANDLER_ADAPTER_REFRESH = 2;// 刷新进度
	/** 配置文件 **/
	public static final String CONFIG_FILENAME = "sys_setting";
	/** 用户信息 **/
	public static final String UID = "uid";// 用户UID
	public static final String NICK = "nick";// 用户昵称
	public static final String HEADER = "header";// 用户头像链接
	public static final String SIGN = "sign";// 用户签名
	public static final String SCORE = "score";// 用户积分
	public static final String LEVEL = "level";// 用户称号
	public static final String BIRTHDAY = "birthday";// 用户生日
	public static final String CITY = "city";// 用户城市
	public static final String QQ = "qq";// 用户QQ
	public static final String WEIXIN = "weixin";// 用户微信
	public static final String SEX = "sex";// 用户性别
	public static final String MAILCOUNT = "mailcount";// 未读邮件条数
	public static final String PHONE = "phone";// 用户手机
	public static final String EMAIL = "email";// 用户邮箱
	public static final String MD = "md"; // 钻石
	public static final String MG = "mg"; // 金币
	public static final String MS = "ms"; // 银币
	public static final String TCOUNT = "tcount";// 任务个数
	public static final String TOKEN = "token";
	public static final String ffg = "ffg";
	public static final String fbg = "fbg";
	public static final String shop = "shop";
	public static final String help = "help";
	public static final String activity = "activity";

	/** 返回消息PID **/
	public static final String MSGPID = "msgpid";
	public static final String BTIME = "btime";// 服务器时间
	/** 各种PD **/
	public static final String ONLINEPD = "onlinepd";// 在线村民PD
	public static final String VILLAGEPD = "villagepd";// 村委会PD
	public static final String VILLAGEPEOPLEPD = "villagepeoplepd";// 财富排行PD
	public static final String VILLAGESOCREPD = "villagescorepd";// 活跃度PD
	public static final String MAILPD = "mailpd"; // 私信PD
	public static final String MAINPD = "mainpd"; // 首页PD
	/** 系统路径,声音文件夹,图片文件夹 **/
	public static final String PROJECT_PATH = "/data/data/";
	public static final String AUDIO_PATH = "/Audio"; // 音频换成文件夹
	public static final String PHOTO_PATH = "/Photo"; // 头像换成文件夹
	public static final String IMAGE_PATH = "/Image"; // 图片换成文件夹
	public static final String SAVE_PATH = "/JrcSaveImage"; // 保存图片
	public static final String OLDPIC_PATH = "oldpicpath"; // 原图
	public static final String NEWPIC_PATH = "newpicpath"; // 压缩图
	public static final String AUDIO_MY_PATH = "audiopath"; // 录音
	public static final String AUDIO_LENGTH = "audiolength"; // 录音大小
	public static final String AUDIOFLAG = "audioflag"; // 音频当前播放标识

	public static final String AUDIOMODEL = "audiomodel"; // 声音播放模式
	public static final String AUDIOAUTO = "audioauto"; // 声音是否自动播放
	public static final String PICAUTO = "picauto";// 图片是否自动播放


	/** 处理标识 **/
	public static final int NONE = 0;
	public static final int ERROR = 4;// 错误代码
	public static final int PHOTOHRAPH = 10;// 拍照
	public static final int PHOTOZOOM = 11; // 缩放
	public static final int PHOTORESOULT = 12;// 结果
	public static final int RIGHTTOP = 6;// 顶部设置
	public static final int RIGHTAUDIO = 7;// 顶部自动播放音频
	public static final int RIGHTPIC = 8; // 顶部自动加载图片
	public static final int RIGHTSOUND = 9; // 顶部设置扬声器
	public static final String IMAGE_UNSPECIFIED = "image/*";
	
	///搜索
	public static final String SEARCHURL = "http://jrc.hutudan.com/usercenter/tjuser.php";
	public static final String SEARCHUPTUIJIAN = "http://jrc.hutudan.com/usercenter/totj.php";
	public static final String MONEYUSER = "http://jrc.hutudan.com/usercenter/moneyuser.php";
	//好友动态
	public static final String DONGTAI ="http://jrc.hutudan.com/usercenter/related_items.php";
	public static final String DTDETATIL= "http://jrc.hutudan.com/usercenter/related_item.php";
//	public static final String ABOUTME= "http://jrc.hutudan.com/usercenter/related_replylist.php";
	public static final String ABOUTME= "http://jrc.hutudan.com/usercenter/related_replylist.php";
	public static final String DYPINGLUN= "http://jrc.hutudan.com/usercenter/related_reply.php";
	//上传照片墙
	public static final String PICUPQIANG = "http://jrc.hutudan.com/usercenter/foto.php";// 图片是否自动播放
	public static final String QIANGDELETE = "http://jrc.hutudan.com/usercenter/delfoto.php";// 删除图片
	/////游戏
	public static final String GAMEROOM = "http://jrc.hutudan.com/game/list.php";
	public static final String GAMEDETATIL = "http://jrc.hutudan.com/game/info.php";
	public static final String GAMEPAIHANG = "http://jrc.hutudan.com/game/top.php";
	public static final String GAMEMOREPAIHANG = "http://jrc.hutudan.com/game/top_m.php";
	public static final String ZHUANPANLOGIN = "http://jrc.hutudan.com/zp/enter.php";
	public static final String ZHUANPANSTART = "http://jrc.hutudan.com/zp/zhuan.php";
	public static final String ZHUANPANHELP = "http://jrc.hutudan.com/zp/help.php";
	public static final String ZHUANPANPHB = "http://jrc.hutudan.com/zp/top.php";
	
	public static final String TIAOZHANTA = "http://jrc.hutudan.com/game/tz.php";
	
	public static final String JUBAOHEADER = "http://jrc.hutudan.com/usercenter/report_header.php";
	public static final String JUBAOPHOTO = "http://jrc.hutudan.com/usercenter/report_foto.php";
	public static final String DOWNGAME = "";
	
	public static final String[] name = { "/微笑", "/撇嘴", "/色", "/发呆", "/得意",
			"/流泪", "/害羞", "/闭嘴", "/睡", "/大哭", "/尴尬", "/发怒", "/调皮", "/呲牙",
			"/惊讶", "/难过", "/酷", "/冷汗", "/抓狂", "/吐", "/偷笑", "/可爱", "/白眼", "/傲慢",
			"/饥饿", "/困", "/惊恐", "/流汗", "/憨笑", "/大兵", "/奋斗", "/咒骂", "/疑问", "/嘘",
			"/晕", "/折磨", "/衰", "/骷髅", "/敲打", "/再见", "/擦汗", "/抠鼻", "/鼓掌",
			"/糗大了", "/坏笑", "/左哼哼", "/右哼哼", "/哈欠", "/鄙视", "/委屈", "/快哭了", "/阴险",
			"/亲亲", "/吓", "/可怜", "/菜刀", "/西瓜", "/啤酒", "/篮球", "/乒乓", "/咖啡", "/饭",
			"/猪头", "/玫瑰", "/凋谢", "/示爱", "/爱心", "/心碎", "/蛋糕", "/闪电", "/炸弹",
			"/刀", "/足球", "/瓢虫", "/便便", "/月亮", "/太阳", "/礼物", "/拥抱", "/强", "/弱",
			"/握手", "/胜利", "/抱拳", "/勾引", "/拳头", "/差劲", "/爱你", "/NO", "/OK",
			"/爱情", "/飞吻", "/跳跳", "/发抖", "/怄火", "/转圈", "/磕头", "/回头", "/跳绳",
			"/挥手", "/激动", "/街舞", "/献吻", "/左太极", "/右太极" };

	// 申请的开发appid
	public static final String APP_ID = "wx8e8dc60535c9cd93";

	public static final String WX_ACTION = "action";

	public static final String WX_ACTION_INVITE = "invite";

	public static final String WX_RESULT_CODE = "ret";

	public static final String WX_RESULT_MSG = "msg";

	public static final String WX_RESULT = "result";
	
	public static final String WEB_DONGHUA = "http://jrc.hutudan.com/usercenter/dj_update.php";
	public static final String BACKPACKS_USE = "http://jrc.hutudan.com/usercenter/usedj.php";
	public static final String BACKPACKS_SELL = "http://jrc.hutudan.com/usercenter/selldj.php";

	public static class ShowMsgActivity {
		public static final String STitle = "showmsg_title";
		public static final String SMessage = "showmsg_message";
		public static final String BAThumbData = "showmsg_thumb_data";

	}

}