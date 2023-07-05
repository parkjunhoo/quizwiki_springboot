
// member_mkt_opt 값 설정하기
function updateMemberMktOpt() {
	  var mktOpt = "";
	  var allOpt = document.getElementById("agreement");
	  var smsOpt = document.getElementById("agreement_sms");
	  var emailOpt = document.getElementById("agreement_email");
	  var notOpt = document.getElementById("disagreement");

	  if (allOpt.checked) {
	    mktOpt = "마케팅 수신 동의";
	  } else if(smsOpt.checked){
		  mktOpt = "sms"
	  } else if(emailOpt.checked){
		  mktOpt = "email"
	  } else if(notOpt.checked){
		  mktOpt = "마케팅 수신 미동의"
	  }
	  
	  console.log("mktOpt: " + mktOpt);
}

