<?php
	$jsoncallback='';
	if(isset($_POST['jsoncallback']))
	{
		$jsoncallback=$_POST['jsoncallback'];
	} else if(isset($_GET['jsoncallback']))
	{
		$jsoncallback=$_GET['jsoncallback'];
	}
	//
	$content='<span id=\'msg\'  style=\'display:none;font-size:13px;line-height:17px;text-align:left;text-color:#622b0a;PADDING-RIGHT:0px;OVERFLOW-Y:auto;PADDING-LEFT:0px;PADDING-BOTTOM:0px;OVERFLOW:auto;WIDTH:196px;PADDING-TOP:0px;LETTER-SPACING:1pt;HEIGHT:199px;\'>用户协议<br/><br/>欢迎使用”么么”！请您保证使用软件遵循以下协议：<br/>一、	用户须承诺不分享转发任何非法的、骚扰性的、中伤他人的、辱骂性的、恐吓性的、伤害性的、庸俗的，淫秽等信息资料；<br/>二、	用户有义务在看到类似内容时向我们反馈；（反馈：在村委会蓝色公告栏里，点击进入即可）<br/>三、	从中国境内向外传输技术性资料时必须符合中国有关法规；<br/>四、	不干扰或扰乱网络服务；<br/>五、	遵守所有使用所有相关的协议、规定和程序；<br/>六、	开发者一经发现用户上传此类内容（色情低俗）有权利在24小时内删除；<br/>七、	鉴于网络的特性，本软件将无可避免地与您产生直接或间接的互动关系，故特此说明本软件对用户个人信息所采取的收集、使用和保护政策，绝对不会将您的任何资料以任何方式泄露给任何一方；<br/>八、	本软件提供的其他互联网上之软件或资源的链接。由于软件无法控制上述软件及资源，您应了解并同意，上述软件或资源是否可供利用，软件不予负责，存在或源于上述软件或资源的任何「内容」、广告、产品或其他资料，软件亦不予担保或负责；<br/>九、	软件所有原创文字、图片、标志等知识产权归软件所有，受相关法律保护。有关知识产权归属的争议请与软件协商解决；任何人（国家法律规定的版权所有人除外）未经软件书面许可，转载、复制发布、发表、广播、播放、出版等活动将导致软件的追究；<br/>十、	由于您将用户密码告知他人或与他人共享注册帐户或者用户密码保护不当、在公共计算机（网吧、办公室、多人共同一台计算机等）上网和计算机被盗用导致资料的泄漏、丢失、被盗用、被篡改，以及由此导致的任何相关责任和任何人的权益损失与本软件无关；<br/><br/>如果你继续使用本软件则表示你已同意本协议，若有意见和建议可以拨打用户热线010-57467116。</span>';
	//
	echo $jsoncallback.'([{"msg":"'.urlencode($content).'"}])';
?>