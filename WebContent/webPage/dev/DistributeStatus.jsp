<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/webPage/common/common.jsp" />

<style>
	   .sbgrid-form #select_option { width: 98%; height: 100px; text-align: center;border: #757575 solid 2px;background-color: #fafafa; margin: 5px 10px 5px 15px;}
	   .sbgrid-form #select_option tr td{width: 5%;}
	   .sbgrid-form #inputData { width: 98%; height: 100%; text-align: center;border: #757575 solid 2px;background-color: #fafafa; margin: 5px 10px 5px 15px;}
	   .sbgrid-form #inputData tr td{ width: 5%; border: #757575 solid 2px;}
	   
	   label{margin-top: 5px;}
	   button{width:80px;}
	   
	  	#sbGridArea{width: 100%; height:500px;}
	  
	   .sbgrid-tem-contents{width : 100%; height:100%;}
	   #titleBar{background-color: #E0F8F7; width:100%; height: 2%;}	   
</style>
	<section>
		<div class="container-fluid" style="border: #757575 solid 2px;  margin: 0px 15px 0px 15px; padding: 5px 0px 5px 0px;  overflow:hidden;">
			<div class="row-fluid">
				<div class="row">
					<div class="col-xs-12 col-sm-1">
						<sbux-label id="idxLabel_norm" name="label_norm" uitype="normal" text="시스템"></sbux-label>
					</div>
					<div class="col-xs-12 col-sm-2">
						<sbux-select id="syscd_combo" name="syscd_combo" uitype="single" jsondata-ref = "syscd_combo" jsondata-text="cm_sysmsg" jsondata-value="cm_syscd"  style="width:100%;"></sbux-select>				
					</div>
					<div class="col-xs-12 col-sm-1">
						<sbux-label id="idxLabel_norm2" name="label_norm2" uitype="normal" text="진행상태"></sbux-label>
					</div>
					<div class="col-xs-12 col-sm-2">
						<sbux-select id="stepcbo" name="stepcbo" uitype="single" jsondata-ref = "stepcbo" jsondata-text="label" jsondata-value="value"  style="width:100%;" onchange="step_combo_change_resultHandler(stepcbo)"></sbux-select>
					</div>
					<div class="col-xs-12 col-sm-1">
						<sbux-label id="idxLabel_norm12" name="label_norm12" uitype="normal" text="처리구분"></sbux-label>
					</div>
					<div class="col-xs-12 col-sm-2">
						<sbux-select id="cboGbn" name="cboGbn" uitype="single" jsondata-ref = "cboGbn" jsondata-text="cm_codename" jsondata-value="cm_micode"  style="width:100%;"></sbux-select>
					</div>
					<div class="col-xs-12 col-sm-2">
						<sbux-radio id="rdo_norm1" name="rdo_norm" uitype="normal" text="신청일기준" value="0" checked></sbux-radio>
						<sbux-radio id="rdo_norm2" name="rdo_norm" uitype="normal" text="완료일기준" value="1"></sbux-radio>
					</div>
					<div class="col-xs-12 col-sm-1">
						<sbux-button id="btn_cmdQry" name="btn_cmdQry" uitype="normal" text="조&nbsp;&nbsp;&nbsp;&nbsp;회" onclick="cmdQry_Proc()"></sbux-button>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12 col-sm-1">
						<sbux-label id="idxLabel_norm3" name="label_norm3" uitype="normal" text="신청종류"></sbux-label>
					</div>
					<div class="col-xs-12 col-sm-2">
						<sbux-select id="reqcd_combo" name="reqcd_combo" uitype="single" jsondata-ref = "reqcd_combo" jsondata-text="cm_codename" jsondata-value="cm_micode"  style="width:100%;"></sbux-select>
					</div>
					<div class="col-xs-12 col-sm-1">
						<sbux-label id="idxLabel_norm4" name="label_norm4" uitype="normal" text="프로그램명/설명"></sbux-label>
					</div>
					<div class="col-xs-12 col-sm-2">
						<sbux-input id="input_text" name="input_text" uitype="text" title="프로그램명/설명" placeholder="프로그램명/설명을 입력하세요." onkeyenter="cmdQry_Proc()"></sbux-input>
					</div>
					<div class="col-xs-12 col-sm-1">
						<sbux-label id="idxLabel_norm5" name="label_norm5" uitype="normal" text="신청자명"></sbux-label>
					</div>
					<div class="col-xs-12 col-sm-2">
						<sbux-input id="input_text2" name="input_text2" uitype="text" title="신청자명" onkeyenter="cmdQry_Proc()" disabled></sbux-input>
					</div>
					<div class="col-xs-12 col-sm-1">
						<sbux-checkbox id="chkbox_norm" name="chkbox_norm" uitype="normal" text="본인건만" checked></sbux-checkbox>
					</div>
					<div class="col-xs-12 col-sm-1">
						<sbux-picker id="datStD" name="datStD" uitype="date" mode="popup" show-button-bar="false" style="width:100%;"></sbux-picker>
						<sbux-picker id="datEdD" name="datEdD" uitype="date" mode="popup" show-button-bar="false" style="width:100%;"></sbux-picker>
					</div>
					<div class="col-xs-12 col-sm-1">
						<sbux-button id="btn_cmdExl" name="btn_cmdExl" uitype="normal" text="엑셀저장" onclick="doDataToExcel()"></sbux-button>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12 col-sm-1" style="background-color: #FF0000;">
						<sbux-label id="idxLabel_norm6" name="label_norm11" uitype="normal" text="반려 또는 취소" style="color:#FFFFFF; padding-bottom:5px;"></sbux-label>
					</div>
					<div class="col-xs-12 col-sm-1" style="background-color: #BE81F7;">
						<sbux-label id="idxLabel_norm7" name="idxLabel_norm7" uitype="normal" text="시스템처리 중 에러발생" style="color:#FFFFFF; padding-bottom:5px;"></sbux-label>
					</div>
					<div class="col-xs-12 col-sm-1" style="background-color: #000000;">
						<sbux-label id="idxLabel_norm8" name="idxLabel_norm8" uitype="normal" text="처리완료" style="color:#FFFFFF; padding-bottom:5px;"></sbux-label>
					</div>
					<div class="col-xs-12 col-sm-1" style="background-color: #0000FF;">
						<sbux-label id="idxLabel_norm9" name="idxLabel_norm9" uitype="normal" text="진행중" style="color:#FFFFFF; padding-bottom:5px;"></sbux-label>
					</div>
					<div class="col-xs-12 col-sm-2">
						<sbux-label id="idxLabel_norm10" name="idxLabel_norm10" uitype="normal" text="SR-ID/SR명/문서번호"></sbux-label>
					</div>
					<div class="col-xs-12 col-sm-4">
						<sbux-input id="input_text3" name="input_text3" uitype="text" title="SR-ID/SR명/문서번호" placeholder="SR-ID/SR명/문서번호를 입력하세요." onkeyenter="cmdQry_Proc()"></sbux-input>
					</div>
					<div class="col-xs-12 col-sm-1">
						<sbux-label id="idxLabel_norm11" name="idxLabel_norm11" uitype="normal" text="총0건"></sbux-label>
					</div>
					<div class="col-xs-12 col-sm-1">
						<sbux-button id="btn_cmdClear" name="btn_cmdClear" uitype="normal" text="초기화"></sbux-button>
					</div>
				</div>
			</div>
		</div>
	</section>
	
	<section>
		<div class="container-fluid" style="height:600px; margin: 0px 15px 0px 15px; padding: 5px 0px 5px 0px;  overflow:hidden;">
				<div id="sbGridArea"></div>
		</div>
	</section>	
	<script type="text/javascript" src="<c:url value="/js/ecams/dev/DistributeStatus.js"/>"></script>
