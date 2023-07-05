
// member_mkt_opt 값 설정하기
function updateMemberMktOpt() {
	  var mktOpt = "";
	  var selectAllCheckbox = document.getElementById("selectall");
	  var smsCheckbox = document.getElementById("aggrement_sms");
	  var emailCheckbox = document.getElementById("aggrement_email");

	  if (selectAllCheckbox.checked) {
	    mktOpt = "마케팅 수신 동의";
	  } else {
	    if (smsCheckbox.checked) {
	      mktOpt = "sms";
	    }
	    if (emailCheckbox.checked) {
	      mktOpt = "email";
	    }
	  }
	  console.log("mktOpt: " + mktOpt);
}

// 체크박스
function checkSelectAll()  {
  // 전체 체크박스
  const checkboxes 
    = document.querySelectorAll('.mkt_opt_sub');
  // 선택된 체크박스
  const checked 
    = document.querySelectorAll('.mkt_opt_sub:checked');
  // select all 체크박스
  const selectAll 
    = document.querySelector('input[name="member_mkt_opt"]');
  
  if(checkboxes.length === checked.length)  {
    selectAll.checked = true;
  }else {
    selectAll.checked = false;
  }
  updateMemberMktOpt();
}

function selectAll(selectAll)  {
	const checkboxes = document.querySelectorAll('.mkt_opt_sub');
  
  checkboxes.forEach((checkbox) => {
    checkbox.checked = selectAll.checked
  })
  updateMemberMktOpt();
}

/*function checkSelectAll()  {
  // 전체 체크박스
  const checkboxes 
    = document.querySelectorAll('input[name="mkt_opt_sub"]');
  // 선택된 체크박스
  const checked 
    = document.querySelectorAll('input[name="mkt_opt_sub"]:checked');
  // select all 체크박스
  const selectAll 
    = document.querySelector('input[name="member_mkt_opt"]');
  
  if(checkboxes.length === checked.length)  {
    selectAll.checked = true;
  }else {
    selectAll.checked = false;
  }

}

function selectAll(selectAll)  {
  const checkboxes 
     = document.getElementsByName('mkt_opt_sub');
  
  checkboxes.forEach((checkbox) => {
    checkbox.checked = selectAll.checked
  })
}*/