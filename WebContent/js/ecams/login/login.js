/**
 * 로그인 기능 정의
 * <pre>
 * <b>History</b>
 * 	작성자: 이용문
 * 	버전 : 1.0
 *  수정일 : 2019-01-29
 */
$(document).ready(function() {
	screenInit();
});

function screenInit() {
	$('#ecamsLoginForm').bind('submit', loginSubmitAction);
	setInput();
}

function setInput() {
	if( getCookie('remember') === 'true'){
		SBUxMethod.set('idx_input_id', 	getCookie('ecams_id'));
		SBUxMethod.set('idx_input_pwd', getCookie('ecams_pwd'));
		SBUxMethod.attr('chkbox_remember','checked','checked');
	}else {
		SBUxMethod.set('idx_input_id', 	'');
		SBUxMethod.set('idx_input_pwd', '');
	}
}

var loginSubmitAction = function(e) {
	console.log('loginSubmitAction');
	e.preventDefault();
    e.stopPropagation();
    
    var validationCheckFlag = checkValidation();
    var selectedRemember 	= SBUxMethod.get('chkbox_remember').chkbox_remember;
    var loginValidReturnStr = null;
    var authCode 			= null;
    var userId 				= null;
    var sessionID			= null;
    if( ! validationCheckFlag ) return;

    if(selectedRemember) {
		setCookie('ecams_id', 	SBUxMethod.get('idx_input_id'));
    	setCookie('ecams_pwd', 	SBUxMethod.get('idx_input_pwd'));
    	setCookie('remember', 	true);
	} else {
		setCookie('ecams_id', 	'');
    	setCookie('ecams_pwd', 	'');
    	setCookie('remember', 	false);
	}
    
	
    loginValidReturnStr = isValidLogin();
    
    authCode= loginValidReturnStr.substring(0,1);
    userId	= loginValidReturnStr.substring(1,loginValidReturnStr.lenght);
    
    /*
  	auth_rtn==0:정상적인 로그인
	auth_rtn==3:비번초기화 후 입력비번이랑 주민번호랑 일치 할때
	auth_rtn==9:형상관리시스템 관리자 정상적인로그인
	*/    
    if ( authCode === '0' || authCode === '3' || authCode === '9') {
    	sessionID = setSessionLoginUser(userId);
    	if( sessionID !== null && sessionID !== undefined ) location.replace('../main/eCAMSBase.jsp?sessionID='+sessionID);
    }
    
    
    /* 
    if (authCode === '2')//에러카운드 초과 했을때
	if (authCode === '7')//DB 사용자정보가 없을때
	if (authCode === '1')//cm_active == 0 일때
	if (authCode === '5')//CM_JUMINNUM 주민번호가 null 일때
	if (authCode === '6')//비번변경 주기 초과 했을 경우
    */
    
    
};

function setSessionLoginUser(userId) {
	var userInfo = {
		userId		: 	JSON.stringify(userId),
		requestType	: 	'SETSESSION'
	}
	return ajaxCallWithJson('/webPage/login/Login', userInfo, 'json');
}

function isValidLogin() {
	var ajaxReturnData = null;
	
	var userInfo = {
		userId		: 	JSON.stringify(SBUxMethod.get('idx_input_id')),
		userPwd		: 	JSON.stringify(SBUxMethod.get('idx_input_pwd')),
		requestType	: 	'ISVALIDLOGIN'
	}
	ajaxReturnData = ajaxCallWithJson('/webPage/login/Login', userInfo, 'json');
	return ajaxReturnData;
}

function checkValidation() {
	var cookieId = SBUxMethod.get('idx_input_id');
	var cookiePwd = SBUxMethod.get('idx_input_pwd');
	var validationFlag = false;
	if( cookieId !== undefined && cookieId !== '' && cookiePwd !== undefined && cookiePwd !== '') validationFlag = true;
	else validationFlag = false;
	
	return validationFlag;
}

function setCookie(name, value) {
	var cookeValue = escape(value);
	var nowDate = new Date();
	var cookieExpires = null;
	nowDate.setMonth(nowDate.getMonth() + 6);
	cookieExpires = nowDate.toGMTString();
	document.cookie = name + '=' + cookeValue + ";expires=" + cookieExpires + ';path=/';
}

function getCookie(cname) {
	var name = cname + "=";
	var decodedCookie = decodeURIComponent(document.cookie);
	var ca = decodedCookie.split(';');
	for(var i = 0; i <ca.length; i++) {
		var c = ca[i];
		while (c.charAt(0) == ' ') {
			c = c.substring(1);
		}
		if (c.indexOf(name) == 0) {
			return c.substring(name.length, c.length);
		}
	}
	return "";
}


