/*****************************************************************************************
	1. program ID	: Cmr3200.java
	2. create date	: 2008.04.08
	3. auth		    : m.s.kang
	4. update date	: 2009.02.21
	5. auth		    : no name
	6. description	: Request List
*****************************************************************************************/

package app.eCmr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import com.ecams.common.dbconn.ConnectionContext;
import com.ecams.common.dbconn.ConnectionResource;
import com.ecams.common.logger.EcamsLogger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
//import app.common.LoggableStatement;
import app.common.LoggableStatement;
import app.common.SystemPath;
import app.common.UserInfo;


/**
 * @author bigeyes
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */


public class Cmr3200{
    /**
     * Logger Class Instance Creation
     * logger
     */
    Logger ecamsLogger  = EcamsLogger.getLoggerInstance();

	/**
	 * 요청현황을 조회합니다.
	 * @param  syscd,reqcd,teamcd,sta,requser,startdt,enddt,userid
	 * @return json
	 * @throws SQLException
	 * @throws Exception
	 */
    public Object[] get_SelectList(String pSysCd,String pReqCd,String pTeamCd,String pStateCd,String pReqUser,
    		String pStartDt,String pEndDt,String pUserId,String pReqpass,String pJobCd, String dategbn, String spms) throws SQLException, Exception {
		Connection        conn        = null;
		PreparedStatement pstmt       = null;
		PreparedStatement pstmt2      = null;
		ResultSet         rs          = null;
		ResultSet         rs2         = null;
		StringBuffer      strQuery    = new StringBuffer();
		ArrayList<HashMap<String, String>>  rtList	= new ArrayList<HashMap<String, String>>();
		HashMap<String, String>			  	rst	 	= null;
		allTrim(pReqUser);

		ConnectionContext connectionContext = new ConnectionResource();

		try {

			String			  ConfName    = "";
			String			  PgmSayu     = "";
			String			  ColorSw     = "";
	        Integer           Cnt         = 0;
	        String            Sunhang	  = "";
	        boolean           sysSw       = false;
	        String            strSysGbn   = "";
	        String            wkTeamCd    = "";
	        String            reqName     = "";
//	        String[]          reltime;
//	        String            strRELTime = "";

			conn = connectionContext.getConnection();

			UserInfo userinfo = new UserInfo();
			boolean adminYn = false;
			adminYn = userinfo.isAdmin(pUserId);

			strQuery.setLength(0);
			strQuery.append("SELECT /*+ NO_CPU_COSTING */ A.CR_ACPTNO,A.CR_ACPTDATE,A.CR_EMGCD,     \n");
			strQuery.append("       A.CR_EDITOR,A.CR_QRYCD,A.CR_SYSCD,A.CR_STATUS,A.cr_passcd,      \n");
			strQuery.append("       A.CR_GYULJAE,A.CR_PRCDATE,A.CR_SYSGB,a.cr_prcreq,               \n");
			strQuery.append("       A.cr_prctime,B.CM_SYSINFO,a.cr_passok,a.cr_closeyn,             \n");
			strQuery.append("       a.cr_itsmid,                                                    \n");
			strQuery.append("       (select cc_reqtitle from cmc0100 where cc_srid=a.cr_itsmid) cr_itsmtitle, \n");
			//정기배포 시간 셋팅하기 위해 조회
			//strQuery.append("       B.cm_reltime,TO_CHAR(SYSDATE,'HH24MM') nowDate,                 \n");
			strQuery.append("       B.CM_SYSMSG,C.CM_DEPTNAME,D.CM_USERNAME,E.CM_CODENAME SIN,      \n");
			strQuery.append("       to_char(a.cr_acptdate,'yyyy/mm/dd hh24:mi') as acptdate,        \n");
			strQuery.append("       to_char(a.cr_prcdate,'yyyy/mm/dd hh24:mi') as prcdate,          \n");
			strQuery.append("       to_char(a.cr_lastdate,'yyyy/mm/dd hh24:mi:ss') as lastdate,        \n");
			strQuery.append("       substr(a.cr_acptno,1,4) || '-' || substr(a.cr_acptno,5,2) ||    \n");
			strQuery.append("       '-' || substr(a.cr_acptno,7,6) as acptno,a.cr_sayucd    \n");
			strQuery.append("  FROM	CMM0020 E,CMM0040 D,CMM0100 C,CMM0030 B,CMR1000 A    \n");
			
			/* 2013. 11. 12. 주석처리
			strQuery.append("       '-' || substr(a.cr_acptno,7,6) as acptno,a.cr_sayucd, g.cm_jobname    \n");			
			strQuery.append("  FROM	CMM0020 E,CMM0040 D,CMM0100 C,CMM0030 B,CMR1000 A, cmm0102 g    \n");
			if(!adminYn){
				strQuery.append("  , CMM0044 F    \n");
			}
			*/
			
			//strQuery.append(" where a.cr_qrycd<'30'                                                 \n");
			strQuery.append("where a.cr_syscd=b.cm_syscd                                            \n");
			strQuery.append("and d.cm_project = c.cm_deptcd                                       \n");
			strQuery.append("and a.cr_editor = d.cm_userid                                        \n");
			strQuery.append("and e.cm_macode='REQUEST' and e.cm_micode=a.cr_qrycd                 \n");

			if (pStateCd == null || pStateCd == "") {//전체일때
				strQuery.append("and ((A.CR_PRCDATE IS NOT NULL                                     \n");
				if(pStartDt.equals("")){
					strQuery.append("  and to_char(a.cr_acptdate,'yyyymmdd')>=to_char(sysdate,'yyyymmdd')      \n");
					strQuery.append("  and to_char(a.cr_acptdate,'yyyymmdd')<=to_char(sysdate,'yyyymmdd'))	    \n");
				}else{
					if(dategbn.equals("0")){
						strQuery.append("  and to_char(a.cr_acptdate,'yyyymmdd')>=?                     \n");
						strQuery.append("  and to_char(a.cr_acptdate,'yyyymmdd')<=?)                    \n");
					}else{
						strQuery.append("  and to_char(a.cr_prcdate,'yyyymmdd')>=?                      \n");
						strQuery.append("  and to_char(a.cr_prcdate,'yyyymmdd')<=?)                     \n");
					}
				}
				strQuery.append("  or   a.cr_status='0')  								 		  \n");
			} else if (pStateCd.equals("1") || pStateCd.equals("2") || pStateCd.equals("4")){//1:미완료(에러+진행중) 2:시스템에러 4:진행중
				strQuery.append("and   a.cr_status='0'                                            \n");
			} else if (pStateCd.equals("3")){//반려
				strQuery.append("and   a.cr_status='3'                                            \n");
				strQuery.append("  and A.CR_PRCDATE IS NOT NULL                                   \n");
				if(dategbn.equals("0")){
					strQuery.append("  and to_char(a.cr_acptdate,'yyyymmdd')>=?                     \n");
					strQuery.append("  and to_char(a.cr_acptdate,'yyyymmdd')<=?                     \n");
				}else{
					strQuery.append("  and to_char(a.cr_prcdate,'yyyymmdd')>=?                      \n");
					strQuery.append("  and to_char(a.cr_prcdate,'yyyymmdd')<=?                      \n");
				}
			}else if (pStateCd.equals("9")){//완료
				strQuery.append("and   A.CR_PRCDATE IS NOT NULL and a.cr_status<>'3'                \n");
				if(dategbn.equals("0")){
					strQuery.append("  and to_char(a.cr_acptdate,'yyyymmdd')>=?                     \n");
					strQuery.append("  and to_char(a.cr_acptdate,'yyyymmdd')<=?                     \n");
				}else{
					strQuery.append("  and to_char(a.cr_prcdate,'yyyymmdd')>=?                      \n");
					strQuery.append("  and to_char(a.cr_prcdate,'yyyymmdd')<=?                      \n");
				}
			}else if (pStateCd.equals("C1")){
				strQuery.append("and   A.CR_PRCDATE IS NOT NULL and a.cr_status<>'3'              \n");
				strQuery.append("and a.cr_acptno in (select a.cr_acptno from cmr1010 a,cmr0020 b  \n");
				strQuery.append("                     where b.cr_status='5'                       \n");
				strQuery.append("                       and b.cr_itemid=a.cr_itemid               \n");
				strQuery.append("                       and substr(a.cr_acptno,5,2) in ('01','02') \n");
				strQuery.append("                       and a.cr_confno is null                   \n");
				strQuery.append("                     group by a.cr_acptno)                       \n");
			}else if (pStateCd.equals("C2")){
				strQuery.append("and   A.CR_PRCDATE IS NOT NULL and a.cr_status<>'3'              \n");
				strQuery.append("and a.cr_acptno in (select a.cr_acptno from cmr1010 a,cmr0020 b  \n");
				strQuery.append("                     where b.cr_status='B'                       \n");
				strQuery.append("                       and b.cr_itemid=a.cr_itemid               \n");
				strQuery.append("                       and substr(a.cr_acptno,5,2)='03'          \n");
				strQuery.append("                       and a.cr_confno is null                   \n");
				strQuery.append("                     group by a.cr_acptno)                       \n");
			}else if (pStateCd.equals("RB")){
				strQuery.append("and   A.CR_PRCDATE IS NOT NULL and a.cr_status<>'3'              \n");
				strQuery.append("and   a.cr_relatno is null                                       \n");
			}
			if (pSysCd != null && pSysCd != "") strQuery.append("and a.cr_syscd = ?               \n");
			if (pReqCd != null && pReqCd != "") {
				strQuery.append("and a.cr_qrycd = ?               \n");
				if(pReqCd.equals("93") || pReqCd.equals("94")) {
					strQuery.append("and a.cr_closeyn = 'Y'              \n");
				}
			}
			if (pTeamCd != null && pTeamCd != "")  {
				strQuery.append("and a.cr_teamcd in (select cm_deptcd from (select * from cmm0100 where cm_useyn='Y')  \n");
				strQuery.append("                      start with cm_updeptcd=?                   \n");
				strQuery.append("                      connect by prior cm_deptcd=cm_updeptcd     \n");
				strQuery.append("                     union    \n");
				strQuery.append("                     select ? from dual) \n");
			}
			if (pReqUser != null && pReqUser.length() > 0) {
					strQuery.append("and a.cr_editor in (select cm_userid from cmm0040\n");
					strQuery.append("                     where (cm_username=?\n");
					strQuery.append("                        or cm_userid=?))\n");
			}

			if(!adminYn){
				strQuery.append("  and a.cr_editor = ?\n");
				/* 2013. 11. 12. 주석처리
				strQuery.append("  and f.cm_userid = ?\n");
				strQuery.append("  and f.cm_syscd = a.cr_syscd\n");
				strQuery.append("  and f.cm_jobcd = a.cr_jobcd\n");
				*/
			}
			if(!pReqpass.equals("ALL")){
				strQuery.append("  and a.cr_passok = ?       \n");
			}
			if (pJobCd != null && pJobCd != "") {
				strQuery.append("  and a.cr_jobcd = ?       \n");
			}
			//strQuery.append("  and a.cr_jobcd = g.cm_jobcd       \n"); // 2013. 11. 12. 주석처리
			if (spms != null && spms != "") {
				strQuery.append("  and (a.cr_itsmid like ? or upper(a.cr_itsmtitle) like upper(?))  \n");
			}
			strQuery.append("order by a.cr_acptdate desc \n");

			
			
            Date start = new Date();
            ecamsLogger.error("Start Query: " + start);
            
            
            
//			pstmt = conn.prepareStatement(strQuery.toString());
			pstmt = new LoggableStatement(conn,strQuery.toString());
            Cnt = 0;
            pstmt.setQueryTimeout(1);
            
			if (((pStateCd == null || pStateCd == "")&&!pStartDt.equals(""))
				|| pStateCd.equals("3")
				|| pStateCd.equals("9")) {
	            pstmt.setString(++Cnt, pStartDt);
	            pstmt.setString(++Cnt, pEndDt);
			}
            if (pSysCd != null && pSysCd != "") pstmt.setString(++Cnt, pSysCd);
            if (pReqCd != null && pReqCd != "") {
            	if(pReqCd.equals("93")) pReqCd = "03"; //테스트폐기
            	if(pReqCd.equals("94")) pReqCd = "04"; //운영폐기
            	
            	pstmt.setString(++Cnt, pReqCd);
            }
			if (pTeamCd != null && pTeamCd != ""){
				pstmt.setString(++Cnt, pTeamCd);
				pstmt.setString(++Cnt, pTeamCd);
			}
			if (pReqUser != null && pReqUser.length() > 0){
				pstmt.setString(++Cnt, pReqUser);
				pstmt.setString(++Cnt, pReqUser);
			}
			if(!adminYn){
				pstmt.setString(++Cnt, pUserId);
			}
			if(!pReqpass.equals("ALL")){
				pstmt.setString(++Cnt, pReqpass);
			}
			if (pJobCd != null && pJobCd != "") {
				pstmt.setString(++Cnt, pJobCd);
			}
			if (spms != null && spms != "") {
				pstmt.setString(++Cnt, "%"+spms+"%");
				pstmt.setString(++Cnt, "%"+spms+"%");
			}
			ecamsLogger.error(((LoggableStatement)pstmt).getQueryString());
            rs = pstmt.executeQuery();


            Date end = new Date();
            ecamsLogger.error("End Query: " + end);
            long diff = end.getTime() - start.getTime();
            ecamsLogger.error("Query time in ms: " + diff);
            
			while (rs.next()){

				ConfName = "";
				PgmSayu = "";
			    ColorSw = "0";
			    Sunhang = "";
			    strSysGbn = "";
			    sysSw = false;
	            if (rs.getString("cr_status").equals("3")) {
	            	ConfName = "반려";
	            	ColorSw = "3";
	            } else if (rs.getString("cr_status").equals("9")) {	//2009_0706 책임자 ->결재완료
	            	ConfName = "처리완료";
	            	ColorSw = "9";
	            } else {
	            	strQuery.setLength(0);
					strQuery.append("select cr_teamcd,cr_team,cr_confname,cr_congbn from cmr9900   \n");
				    strQuery.append(" where cr_acptno=? and cr_locat='00'        \n");
					pstmt2 = conn.prepareStatement(strQuery.toString());
					//pstmt2 = new LoggableStatement(conn,strQuery.toString());
		            pstmt2.setString(1, rs.getString("cr_acptno"));
		            //ecamsLogger.error(((LoggableStatement)pstmt2).getQueryString());
		            rs2 = pstmt2.executeQuery();
		            if (rs2.next()){
		            	strSysGbn = rs2.getString("cr_team");
		            	ConfName = rs2.getString("cr_confname");
		            	if (rs2.getString("cr_teamcd").equals("8")){
			            	ConfName = "처리완료";
		            	}
		            	if (rs2.getString("cr_teamcd").equals("1")){
		            		sysSw = true;
		            		ConfName = rs2.getString("cr_confname") + "중";
		            	}
		            	if (rs2.getString("cr_congbn").equals("4")){
		            		ConfName = ConfName + "(후결)";
		            	}
		            	wkTeamCd = 	rs2.getString("cr_teamcd");
		            	if (rs.getString("cm_sysinfo").substring(5,6).equals("1") && rs.getString("cr_prcdate") == null) {
			            	if (rs.getString("cr_passok").equals("0") && rs.getString("cr_prctime") != null) {

			            		/*//정기적용 시간 셋팅 부분
			            		reltime = new String[rs.getString("cr_reltime").split(",").length];
			            		reltime = rs.getString("cr_reltime").split(",");
			            		strRELTime = reltime[0];
			            		//마지막 결재 시간 체크
			            		strQuery.setLength(0);
								strQuery.append("select cr_teamcd,cr_team,cr_confname,cr_congbn from cmr9900   \n");
							    strQuery.append(" where cr_acptno=? and cr_locat='00'        \n");
								pstmt2 = conn.prepareStatement(strQuery.toString());
								//pstmt2 = new LoggableStatement(conn,strQuery.toString());
					            pstmt2.setString(1, rs.getString("cr_acptno"));
					            //ecamsLogger.error(((LoggableStatement)pstmt2).getQueryString());
					            rs2 = pstmt2.executeQuery();
					            if (rs2.next()){
					            }
			            		for(Cnt=0 ; Cnt<reltime.length ; Cnt++)
			            		{
			            		}
			            		reltime = null;
			            		ConfName = "[정기적용("+rs.getString("cr_prctime").substring(0,2)+":"+rs.getString("cr_prctime").substring(2,4)+")] "+ConfName;
			            		*/
			            		ConfName = "[정기적용]"+ConfName;
			            	}
		            	}
		            }
		            rs2.close();
		            pstmt2.close();
	            }
	            if (wkTeamCd.equals("3")){
	            	strQuery.setLength(0);
					strQuery.append("select cm_username from cmm0040   \n");
				    strQuery.append(" where cm_userid = ?              \n");
					pstmt2 = conn.prepareStatement(strQuery.toString());
					//pstmt2 = new LoggableStatement(conn,strQuery.toString());
		            pstmt2.setString(1, strSysGbn);
		            //ecamsLogger.error(((LoggableStatement)pstmt2).getQueryString());
		            rs2 = pstmt2.executeQuery();
		            if (rs2.next()){
		            	ConfName = ConfName + " [" + rs2.getString("cm_username") + "] 결재 대기중";
		            }
		            rs2.close();
		            pstmt2.close();

	            }
	            if (rs.getString("cr_prcdate") == null && sysSw == true) {
	            	if (Integer.parseInt(rs.getString("cr_qrycd")) < 30) {
	            		strQuery.setLength(0);
						strQuery.append("select count(*) as cnt from cmr1010   \n");
					    strQuery.append(" where cr_acptno=?                    \n");
					    strQuery.append("   and cr_putcode is not null         \n");
					    strQuery.append("   and cr_prcdate is null             \n");
					    strQuery.append("   and nvl(cr_putcode,'RTRY')!='RTRY' \n");
					    strQuery.append("   and nvl(cr_putcode,'0000')!='0000' \n");
					    //strQuery.append("   and nvl(cr_putcode,'0000')!='WAIT' \n");
					    pstmt2 = conn.prepareStatement(strQuery.toString());
						//pstmt2 = new LoggableStatement(conn,strQuery.toString());
					    pstmt2.setString(1, rs.getString("cr_acptno"));
			            ////ecamsLogger.error(((LoggableStatement)pstmt2).getQueryString());
			            rs2 = pstmt2.executeQuery();
			            if (rs2.next()) {
			            	if (!rs.getString("cr_qrycd").equals("46")){
			            		if (rs2.getInt("cnt") > 0) ColorSw = "5";
			            	}
			            }

			            rs2.close();
			            pstmt2.close();

			            if (!ColorSw.equals("5")) {
			            	strQuery.setLength(0);
			            	/*
			            	select count(*) as cnt from cmr1011 a,
			                (select cr_serno from cmr1010
			                  where cr_acptno='201116000362'
			                    and cr_status='0'
			                    and nvl(cr_putcode,'0000')<>'0000') b
			           where a.cr_acptno='201116000362'
			             and a.cr_prcsys='SYSUP'
			             and a.cr_serno=b.cr_serno
			             and (nvl(a.cr_prcrst,'0000')!='0000'
			              or nvl(a.cr_wait,'0')='W')*/
							strQuery.append("select count(*) as cnt from cmr1011 a,  \n");
							strQuery.append("     (select cr_serno from cmr1010      \n");
							strQuery.append("       where cr_acptno=?                \n");
							strQuery.append("         and cr_status='0'              \n");
							strQuery.append("         and nvl(cr_putcode,'0000')<>'0000') b \n");
						    strQuery.append(" where a.cr_acptno=?                    \n");
						    strQuery.append("   and a.cr_prcsys=?                    \n");
						    strQuery.append("   and a.cr_serno=b.cr_serno            \n");
						    strQuery.append("   and (nvl(a.cr_prcrst,'0000')!='0000' \n");
						    strQuery.append("    or nvl(a.cr_wait,'0')='W')          \n");
							pstmt2 = conn.prepareStatement(strQuery.toString());
							//pstmt2 = new LoggableStatement(conn,strQuery.toString());
				            pstmt2.setString(1, rs.getString("cr_acptno"));
				            pstmt2.setString(2, rs.getString("cr_acptno"));
				            pstmt2.setString(3, strSysGbn);
				            ////ecamsLogger.error(((LoggableStatement)pstmt2).getQueryString());
				            rs2 = pstmt2.executeQuery();
				            if (rs2.next() && rs2.getInt("cnt") > 0){
				            	ColorSw = "5";
				            }
				            rs2.close();
				            pstmt2.close();
		            	}
			            if (!ColorSw.equals("5")) {
			            	strQuery.setLength(0);
							strQuery.append("select count(*) as cnt   \n");
							strQuery.append("  from cmr1011 a,cmr1010 b \n");
						    strQuery.append(" where a.cr_acptno=? \n");
						    strQuery.append("   and b.cr_acptno=a.cr_acptno \n");
						    strQuery.append("   and b.cr_status='0' \n");
						    strQuery.append("   and a.cr_prcsys=? \n");
						    strQuery.append("   and a.cr_serno=b.cr_serno \n");
						    strQuery.append("   and (nvl(a.cr_prcrst,'0000')!='0000' \n");
						    strQuery.append("    or nvl(a.cr_wait,'0')='W') \n");
							pstmt2 = conn.prepareStatement(strQuery.toString());
				            pstmt2.setString(1, rs.getString("cr_acptno"));
				            pstmt2.setString(2, strSysGbn);
				            rs2 = pstmt2.executeQuery();
				            if (rs2.next() && rs2.getInt("cnt") > 0){
				            	ColorSw = "5";
				            }
				            rs2.close();
				            pstmt2.close();
		            	}
	            	}
	            }
	            else if (ColorSw.equals("0") && rs.getString("cr_prcdate") != null){
	            	ColorSw = "9";
	            }

	            if (Integer.parseInt(rs.getString("cr_qrycd")) < 30) {
	            	strQuery.setLength(0);
					strQuery.append("select a.cr_rsrcname from cmr1010 a            \n");
				    strQuery.append(" where a.cr_acptno=?                           \n");
				    strQuery.append("   and ((a.cr_itemid is null and a.cr_baseitem is null) \n");
				    strQuery.append("     or  a.cr_itemid=a.cr_baseitem)            \n");
					pstmt2 = conn.prepareStatement(strQuery.toString());
					//pstmt2 = new LoggableStatement(conn,strQuery.toString());
		            pstmt2.setString(1, rs.getString("cr_acptno"));
		            ////ecamsLogger.error(((LoggableStatement)pstmt2).getQueryString());
		            rs2 = pstmt2.executeQuery();
		            if (rs2.next()){
		            	PgmSayu = rs2.getString("cr_rsrcname");
		            }
		            rs2.close();
		            pstmt2.close();

	            	strQuery.setLength(0);
					strQuery.append("select count(*) as cnt from cmr1010 a          \n");
				    strQuery.append(" where a.cr_acptno=?                           \n");
				    strQuery.append("   and ((a.cr_itemid is null and a.cr_baseitem is null) \n");
				    strQuery.append("     or  a.cr_itemid=a.cr_baseitem)            \n");
					pstmt2 = conn.prepareStatement(strQuery.toString());
					//pstmt2 = new LoggableStatement(conn,strQuery.toString());
		            pstmt2.setString(1, rs.getString("cr_acptno"));
		            ////ecamsLogger.error(((LoggableStatement)pstmt2).getQueryString());
		            rs2 = pstmt2.executeQuery();
		            if (rs2.next()){
		            	if (rs2.getInt("cnt") > 1)
		            	   PgmSayu = PgmSayu + "외 " + Integer.toString(rs2.getInt("cnt") - 1) + "건";
		            }
		            rs2.close();
		            pstmt2.close();

	            }
	            strQuery.setLength(0);
				strQuery.append("select count(*) as cnt from cmr1030 					 \n");
			    strQuery.append(" where cr_acptno=? or cr_befact=?                       \n");
			    pstmt2 = conn.prepareStatement(strQuery.toString());
				//pstmt2 = new LoggableStatement(conn,strQuery.toString());
	            pstmt2.setString(1, rs.getString("cr_acptno"));
	            pstmt2.setString(2, rs.getString("cr_acptno"));
	            ////ecamsLogger.error(((LoggableStatement)pstmt2).getQueryString());
	            rs2 = pstmt2.executeQuery();
	            if (rs2.next()){
	            	if (rs2.getInt("cnt") > 0) Sunhang = "유";
	            	else Sunhang = "무";
	            }
	            rs2.close();
	            pstmt2.close();

	            if (rs.getString("prcdate") != null) {
	            	ConfName = ConfName + "["+rs.getString("prcdate")+"]";
	            }

	            rst = new HashMap<String, String>();
	            //rst.put("rows",    Integer.toString(rs.getRow()));  //NO
        		rst.put("syscd",   rs.getString("cm_sysmsg"));      //시스템
        		//rst.put("cm_jobname", rs.getString("cm_jobname"));  //업무
        		rst.put("acptno",  rs.getString("acptno"));         //요청번호
        		rst.put("deptname",rs.getString("cm_deptname"));    //팀명
        		rst.put("editor",  rs.getString("cm_username"));    //요청자
        		if (rs.getString("cr_itsmid") != null) {
        			rst.put("spms", "["+rs.getString("cr_itsmid")+"] "+rs.getString("cr_itsmtitle"));
        		}
        		//rst.put("qrycd",   rs.getString("sin"));	        //요청구분
        		//rst.put("sayu",    rs.getString("cr_sayu"));	    //요청구분
        		if (rs.getString("cr_closeyn").equals("Y")) {
					if (rs.getString("cr_qrycd").equals("03")) reqName = "테스트폐기";
					else if (rs.getString("cr_qrycd").equals("04")) reqName = "테스트폐기";//"운영폐기"; //운영으로 반영시 주석해제
					else reqName = rs.getString("sin");
					if (rs.getString("cr_passok").equals("2")){ 
						rst.put("qrycd","[긴급]" + reqName);
					} else{
						rst.put("qrycd",reqName);
					}
				} else {
					if (rs.getString("cr_passok").equals("2")){ 
						rst.put("qrycd","[긴급]" + rs.getString("sin"));
					} else{
						rst.put("qrycd",rs.getString("sin"));
					}
				}
        		rst.put("sayu",    rs.getString("cr_passcd"));	    //요청구분
        		rst.put("acptdate",rs.getString("acptdate"));       //요청일시
        		rst.put("sta",     ConfName);                       //상태
        		rst.put("pgmid",   PgmSayu);  	                    //프로그램명
        		rst.put("qrycd2",  rs.getString("cr_qrycd"));       //Qrycd
        		rst.put("editor2",  rs.getString("cr_editor"));     //Editor
        		rst.put("sysgb",  rs.getString("cr_sysgb"));        //SysGb
        		rst.put("endyn",  rs.getString("cr_status"));       //처리완료여부
        		rst.put("syscd2",  rs.getString("cr_syscd"));       //SysCd
        		rst.put("acptno2",  rs.getString("cr_acptno"));     //AcptNo
        		

        		//if(rs.getString("cr_qrycd").equals("04")){
        			if (rs.getString("cr_passok").equals("0")){//수시적용
            			rst.put("passok",  "수시적용");
            		}else if (rs.getString("cr_passok").equals("2")){//긴급적용
            			rst.put("passok",  "긴급");
            		}else if (rs.getString("cr_passok").equals("4")){//일반적용
            			rst.put("passok",  "일반적용");
            		}else {
            			rst.put("passok",  "처리구분X");
            		}
        		//}else{
        		//	rst.put("passok",  "수시적용");
        		//}
        		/*
        		 *
	        		if (rs.getString("cr_passok").equals("0")){//일반신청
	        			rst.put("passok",  "일반적용");
	        		}else if (rs.getString("cr_passok").equals("2")){//긴급
	        			rst.put("passok",  "긴급");
	        		}else if (rs.getString("cr_passok").equals("4")){//적용일시
	        			rst.put("passok",  "적용일시");
	        		}else {
	        			rst.put("passok",  "처리구분X");
	        		}
        		 */
        		rst.put("Sunhang",  Sunhang);     //선행작업 유무
        		rst.put("befsw", "N");
        		if (rs.getString("cr_qrycd").equals("04") && rs.getString("cr_prcdate") == null) {
					strQuery.setLength(0);
					strQuery.append("select count(*) cnt from cmr1030       \n");
					strQuery.append(" where cr_befact=? and cr_autolink='N' \n");
					pstmt2 = conn.prepareStatement(strQuery.toString());
		        	pstmt2.setString(1, rs.getString("cr_acptno"));
		        	rs2 = pstmt2.executeQuery();
		        	if (rs2.next()) {
		        		if (rs2.getInt("cnt")>0) rst.put("befsw", "Y");
		        	}
		        	rs2.close();
		        	pstmt2.close();
        		}
			    if (rs.getString("cr_sayucd") != null){
			    	strQuery.setLength(0);
			    	strQuery.append("select cm_codename from cmm0020 \n");
			    	strQuery.append("where cm_macode='REQSAYU' and \n");
			    	strQuery.append("cm_micode= ? \n");
					pstmt2 = conn.prepareStatement(strQuery.toString());
					pstmt2.setString(1,rs.getString("cr_sayucd"));
					rs2 = pstmt2.executeQuery();
					if (rs2.next()){
						rst.put("sayucd", rs2.getString("cm_codename"));
					}else{
						rst.put("sayucd", "");
					}
					rs2.close();
					pstmt2.close();
				}else{
					rst.put("sayucd", "");
				}
				if (rs.getString("prcdate") != null) rst.put("prcdate", rs.getString("prcdate"));
				else if (sysSw) {
					if (strSysGbn.equals("SYSUA") || strSysGbn.equals("SYSGB") ||
						strSysGbn.equals("SYSED") || strSysGbn.equals("SYSRC")) {
						rst.put("lastdate",  rs.getString("lastdate"));     //AcptNo
					}
				}
				if (rs.getString("cr_prcreq") != null)
					rst.put("prcreq", rs.getString("cr_prcreq").substring(0, 4) + "/" +
							rs.getString("cr_prcreq").substring(4, 6) + "/" +
							rs.getString("cr_prcreq").substring(6, 8) + " " +
							rs.getString("cr_prcreq").substring(8, 10) + ":" +
							rs.getString("cr_prcreq").substring(10, 12));

        		rst.put("colorsw",  ColorSw);
        		rst.put("test",  "");
        		if (pStateCd.equals("2")){//시스템에러만 조회
        			if (ColorSw.equals("5")) rtList.add(rst);
        		} else if (pStateCd.equals("4")){//진행중건만 조회
        			if (!ColorSw.equals("5")) rtList.add(rst);
        		} else{
        			rtList.add(rst);
        		}
        		rst = null;
			}//end of while-loop statement

			rs.close();
			pstmt.close();
			conn.close();
			//ecamsLogger.error("+++++ query end +++++");
			rs = null;
			pstmt = null;
			rs2 = null;
			pstmt2 = null;
			conn = null;

			return rtList.toArray();

		} catch (SQLException sqlexception) {
			sqlexception.printStackTrace();
			ecamsLogger.error("## Cmr3200.SelectList() SQLException START ##");
			ecamsLogger.error("## Error DESC : ", sqlexception);
			ecamsLogger.error("## Cmr3200.SelectList() SQLException END ##");
			throw sqlexception;
		} catch (Exception exception) {
			exception.printStackTrace();
			ecamsLogger.error("## Cmr3200.SelectList() Exception START ##");
			ecamsLogger.error("## Error DESC : ", exception);
			ecamsLogger.error("## Cmr3200.SelectList() Exception END ##");
			throw exception;
		}finally{
			if (strQuery != null) 	strQuery = null;
			if (rtList != null)	rtList = null;
			if (rs != null)     try{rs.close();}catch (Exception ex){ex.printStackTrace();}
			if (rs2 != null)    try{rs2.close();}catch (Exception ex){ex.printStackTrace();}
			if (pstmt != null)  try{pstmt.close();}catch (Exception ex2){ex2.printStackTrace();}
			if (pstmt2 != null)  try{pstmt2.close();}catch (Exception ex2){ex2.printStackTrace();}
			if (conn != null){
				try{
					conn.close();
				}catch(Exception ex3){
					ecamsLogger.error("## Cmr3200.SelectList() connection release exception ##");
					ex3.printStackTrace();
				}
			}
		}
	}//end of SelectList() method statement

    public static String allTrim(String s)
    {
        if (s == null)
            return null;
        else if (s.length() == 0)
            return "";

        int len = s.length();
        int i = 0;
        int j = len;

        for (i = 0; i < len; i++) {
            if ( s.charAt(i) != ' ' && s.charAt(i) != 't' && s.charAt(i) != 'r' && s.charAt(i) != 'n' )
                break;
        }
        if (i == len)
            return "";

        for (j = len - 1; j >= i; j--) {
            if ( s.charAt(i) != ' ' && s.charAt(i) != 't' && s.charAt(i) != 'r' && s.charAt(i) != 'n' )
                break;
        }
        return s.substring(i, j + 1);
    }


    public Object[] get_SelectDashBoard(String UserId,String Cnt) throws SQLException, Exception {
		Connection        conn        = null;
		PreparedStatement pstmt       = null;
		ResultSet         rs          = null;
		StringBuffer      strQuery    = new StringBuffer();
		ArrayList<HashMap<String, String>>  rtList	= new ArrayList<HashMap<String, String>>();
		HashMap<String, String>			  	rst	 	= null;

		ConnectionContext connectionContext = new ConnectionResource();

		try {

			conn = connectionContext.getConnection();

			strQuery.setLength(0);
			strQuery.append("SELECT '요청건' GBN,A.CR_ACPTNO,a.cr_qrycd    \n");
            strQuery.append("     , TO_CHAR(A.CR_ACPTDATE, 'YYYY-MM-DD hh24:mi') CR_ACPTDATE \n");
            strQuery.append("     , SUBSTR(A.CR_SAYU,0,55)               CR_TITLE    \n");
            strQuery.append("     , B.CM_CODENAME REQUEST                \n");
            strQuery.append("     , D.CM_USERNAME USERNAME               \n");
            strQuery.append("     , C.CR_CONFNAME CONFNAME               \n");
            strQuery.append("  FROM CMM0040 D,CMR9900 C,CMM0020 B,CMR1000 A        \n");
            strQuery.append(" WHERE A.CR_EDITOR=? AND A.CR_STATUS='0'    \n");
            strQuery.append("   AND A.CR_ACPTNO=C.CR_ACPTNO              \n");
            strQuery.append("   AND C.CR_LOCAT='00'                      \n");
            strQuery.append("   AND B.CM_MACODE='REQUEST'                \n");
            strQuery.append("   AND B.CM_MICODE=A.CR_QRYCD               \n");
            strQuery.append("   AND A.CR_EDITOR=D.CM_USERID              \n");
            strQuery.append("UNION             \n");
			strQuery.append("SELECT '결재대상' GBN,A.CR_ACPTNO,a.cr_qrycd  \n");
            strQuery.append("     , TO_CHAR(A.CR_ACPTDATE, 'YYYY-MM-DD') CR_ACPTDATE \n");
            strQuery.append("     , SUBSTR(A.CR_SAYU,0,55)               CR_TITLE    \n");
            strQuery.append("     , B.CM_CODENAME REQUEST                \n");
            strQuery.append("     , C.CM_USERNAME USERNAME               \n");
            strQuery.append("     , D.CR_CONFNAME CONFNAME               \n");
            strQuery.append("  FROM CMR9900 D,CMM0040 C,CMM0020 B,CMR1000 A \n");
            strQuery.append(" WHERE A.CR_STATUS NOT IN ('3','9')         \n");
            strQuery.append("   AND A.CR_ACPTNO=D.CR_ACPTNO              \n");
            strQuery.append("   AND D.CR_LOCAT='00' AND D.CR_STATUS='0'  \n");
            strQuery.append("   AND D.CR_TEAMCD IN ('2','3','5','6','7') \n");
            strQuery.append("   AND D.CR_TEAM=?                          \n");
            strQuery.append("   AND B.CM_MACODE='REQUEST'                \n");
            strQuery.append("   AND B.CM_MICODE=A.CR_QRYCD               \n");
            strQuery.append("   AND A.CR_EDITOR=C.CM_USERID              \n");
            strQuery.append("UNION             \n");
			strQuery.append("SELECT '결재대상' GBN,A.CR_ACPTNO,a.cr_qrycd  \n");
            strQuery.append("     , TO_CHAR(A.CR_ACPTDATE, 'YYYY-MM-DD') CR_ACPTDATE \n");
            strQuery.append("     , SUBSTR(A.CR_SAYU,0,55)               CR_TITLE    \n");
            strQuery.append("     , B.CM_CODENAME REQUEST                \n");
            strQuery.append("     , C.CM_USERNAME USERNAME               \n");
            strQuery.append("     , D.CR_CONFNAME CONFNAME               \n");
            strQuery.append("  FROM CMD0304 E,CMR9900 D,CMM0040 C,CMM0020 B,CMR1000 A \n");
            strQuery.append(" WHERE A.CR_STATUS NOT IN ('3','9')         \n");
            strQuery.append("   AND A.CR_ACPTNO=D.CR_ACPTNO              \n");
            strQuery.append("   AND D.CR_LOCAT='00' AND D.CR_STATUS='0'  \n");
            strQuery.append("   AND D.CR_TEAMCD = 'P'                    \n");
            strQuery.append("   AND D.CR_TEAM=E.CD_PRJJIK                \n");
            //strQuery.append("   AND A.CR_isrid=E.CD_isrid                \n");
            strQuery.append("   AND E.CD_PRJUSER=? AND E.CD_CLOSEDT IS NULL\n");
            strQuery.append("   AND B.CM_MACODE='REQUEST'                \n");
            strQuery.append("   AND B.CM_MICODE=A.CR_QRYCD               \n");
            strQuery.append("   AND A.CR_EDITOR=C.CM_USERID              \n");
            strQuery.append("UNION             \n");
			strQuery.append("SELECT '결재대상' GBN,A.CR_ACPTNO,a.cr_qrycd  \n");
            strQuery.append("     , TO_CHAR(A.CR_ACPTDATE, 'YYYY-MM-DD') CR_ACPTDATE \n");
            strQuery.append("     , SUBSTR(A.CR_SAYU,0,55)               CR_TITLE    \n");
            strQuery.append("     , B.CM_CODENAME REQUEST                \n");
            strQuery.append("     , C.CM_USERNAME USERNAME               \n");
            strQuery.append("     , D.CR_CONFNAME CONFNAME               \n");
            strQuery.append("  FROM CMM0043 E,CMR9900 D,CMM0040 C,CMM0020 B,CMR1000 A \n");
            strQuery.append(" WHERE A.CR_STATUS NOT IN ('3','9')         \n");
            strQuery.append("   AND A.CR_ACPTNO=D.CR_ACPTNO              \n");
            strQuery.append("   AND D.CR_LOCAT='00' AND D.CR_STATUS='0'  \n");
            strQuery.append("   AND E.CM_USERID=?                        \n");
            strQuery.append("   AND D.CR_TEAMCD NOT IN ('2','3','5','6','7','P') \n");
            strQuery.append("   AND D.CR_TEAM=E.CM_RGTCD                 \n");
            strQuery.append("   AND B.CM_MACODE='REQUEST'                \n");
            strQuery.append("   AND B.CM_MICODE=A.CR_QRYCD               \n");
            strQuery.append("   AND A.CR_EDITOR=C.CM_USERID              \n");
            strQuery.append("   AND ROWNUM     <  ?                      \n");
            strQuery.append("   order by  GBN,CR_ACPTDATE desc             \n");

            pstmt = conn.prepareStatement(strQuery.toString());
            pstmt.setString(1, UserId);
            pstmt.setString(2, UserId);
            pstmt.setString(3, UserId);
            pstmt.setString(4, UserId);
            pstmt.setInt(5, Integer.parseInt(Cnt));

            rs = pstmt.executeQuery();
            //ecamsLogger.debug(rs.getRow()+"");
			while (rs.next()){
			//ecamsLogger.debug(rs.getString("CM_TITLE"));
				rst = new HashMap<String, String>();
				rst.put("gbncd", rs.getString("GBN"));
				rst.put("cr_acptno", rs.getString("cr_acptno"));
				rst.put("cr_qrycd", rs.getString("cr_qrycd"));
				rst.put("acptdate", rs.getString("CR_ACPTDATE"));
				rst.put("cr_sayu", rs.getString("CR_TITLE"));
				rst.put("qrycd", rs.getString("REQUEST"));
				rst.put("editor", rs.getString("USERNAME"));
				rst.put("confname", rs.getString("CONFNAME"));
				rtList.add(rst);
				rst = null;
				
				if (rtList.size()>=Integer.parseInt(Cnt)) break;
			}//end of while-loop statement


			rs.close();
			pstmt.close();
			conn.close();

			rs = null;
			pstmt = null;
			conn = null;

			return rtList.toArray();

		} catch (SQLException sqlexception) {
			sqlexception.printStackTrace();
			ecamsLogger.error("## Cmr3200.get_SelectDashBoard() SQLException START ##");
			ecamsLogger.error("## Error DESC : ", sqlexception);
			ecamsLogger.error("## Cmr3200.get_SelectDashBoard() SQLException END ##");
			throw sqlexception;
		} catch (Exception exception) {
			exception.printStackTrace();
			ecamsLogger.error("## Cmr3200.get_SelectDashBoard() Exception START ##");
			ecamsLogger.error("## Error DESC : ", exception);
			ecamsLogger.error("## Cmr3200.get_SelectDashBoard() Exception END ##");
			throw exception;
		}finally{
			if (strQuery != null) 	strQuery = null;
			if (rtList != null)	rtList = null;
			if (rs != null)     try{rs.close();}catch (Exception ex){ex.printStackTrace();}
			if (pstmt != null)  try{pstmt.close();}catch (Exception ex2){ex2.printStackTrace();}
			if (conn != null){
				try{
					conn.close();
				}catch(Exception ex3){
					ecamsLogger.error("## Cmr3200.get_SelectDashBoard() connection release exception ##");
					ex3.printStackTrace();
				}
			}
		}
	}//end of get_SelectDashBoard() method statement

    public String reqCncl(String AcptNo,String UserId,String conMsg,String ConfUsr) throws SQLException, Exception {
		Connection        conn        = null;
		PreparedStatement pstmt       = null;
		ResultSet         rs          = null;
		StringBuffer      strQuery    = new StringBuffer();
		SystemPath		  systemPath = new SystemPath();
		String  binpath;
		String[] chkAry;
		Runtime  run = null;
		Process p = null;
		int		cmdrtn;
		String retmsg = "0";
		ConnectionContext connectionContext = new ConnectionResource();

		try {
			conn = connectionContext.getConnection();

			binpath = systemPath.getTmpDir_conn("15",conn);
			run = Runtime.getRuntime();

			//chkAry = new String[4];
			//chkAry[0] = "/bin/sh";
			//chkAry[1] = binpath+"/procck2";
			//chkAry[2] = "ecams_acct";
			//chkAry[3] = AcptNo;
			
			chkAry = new String[3];
			chkAry[0] = binpath+"/procck2";
			chkAry[1] = "ecams_acct";
			chkAry[2] = AcptNo;

			p = run.exec(chkAry);
			p.waitFor();
			cmdrtn = p.exitValue();
			//ecamsLogger.error(chkAry[0]+" " + chkAry[1] + " " + chkAry[2] + " " );
			chkAry = null;
			if (cmdrtn > 0) {
				p = null;
				run = null;
	        	pstmt = null;
	        	conn = null;
				return "2";
			}

			Cmr0250 cmr0250 = new Cmr0250();
			Object[] reqObj = cmr0250.getReqList(UserId,AcptNo);
			HashMap<String,String> rData = (HashMap<String, String>) reqObj[0];
			UserInfo userinfo = new UserInfo();
			boolean admin = userinfo.isAdmin_conn(UserId, conn);
			boolean gyul = false;

			if(rData.get("signteam")!=null){
				if(rData.get("signteam").length()>2){
					if (rData.get("prcsw").equals("0") && rData.get("signteam").substring(0,3).equals("SYS")) {
						if (admin || rData.get("cr_editor").equals(UserId)) {
							if (rData.get("cr_editor").equals(UserId) && rData.get("updtsw3").equals("1")) {
			   		         	if (rData.get("prcsw").equals("Y")) {
			   		            	if (admin || !rData.get("cr_qrycd").equals("04")) gyul = true;
			   		         	} else {
			   		         		if (!rData.get("signteam").equals("SYSRC")) gyul = true;
			   		         	}
			   		      	} else if (admin) {
			   		      		gyul = true;
			   		      	}
			   		   	}
			   		}
				}
			}
			if (rData.get("prcsw").equals("0") && admin) {
	   			gyul = true;
			} else if (rData.get("prcsw").equals("0") && rData.get("updtsw3").equals("1")) {
				if (admin || rData.get("cr_editor").equals(UserId)) {
	   		   	   	if (rData.get("prcsw").equals("Y")) {
						if (rData.get("cr_qrycd").equals("04") && admin) gyul = true;
	   		   	   	} else gyul = true;
	   		   	}
	   		}

//			ecamsLogger.error("admin:"+admin+",gyul:"+gyul+"\n");
//			ecamsLogger.error("signteam:"+rData.get("signteam")+",prcsw:"+rData.get("prcsw")+"\n");
//			ecamsLogger.error("cr_editor:"+rData.get("cr_editor")+",updtsw3:"+rData.get("updtsw3")+"cr_qrycd:"+rData.get("cr_qrycd")+"\n");


			if( gyul ){
	        	strQuery.setLength(0);
	        	strQuery.append("Begin CMR9900_STR ( ");
	        	strQuery.append("?, ?, ?, '9', ?, '9' ); End;");
	        	pstmt = conn.prepareStatement(strQuery.toString());
	        	pstmt.setString(1, AcptNo);
	        	pstmt.setString(2,UserId);
	        	pstmt.setString(3,conMsg);
	        	pstmt.setString(4,ConfUsr);
	        	pstmt.executeUpdate();
	        	pstmt.close();

				run = Runtime.getRuntime();
				chkAry = new String[3];
				chkAry[0] = "/bin/sh";
				chkAry[1] = binpath+"/ecams_acct_bakdel";
				chkAry[2] = AcptNo;
				p = run.exec(chkAry);
				p.waitFor();
				p.exitValue();
				chkAry = null;

	        	retmsg = "0";
			}else{
				retmsg = "1";
			}
        	/*
        	strQuery.setLength(0);
    		strQuery.append("select a.cr_acptno,a.cr_confusr      \n");
    		strQuery.append("  from (Select cr_acptno             \n");
    		strQuery.append("          from cmr1030               \n");
    		strQuery.append("          start with cr_befact=?     \n");
    		strQuery.append("        connect by prior             \n");
    		strQuery.append("             cr_acptno=cr_befact) b, \n");
    		strQuery.append("  cmr9900 a                          \n");
    		strQuery.append(" where b.cr_acptno=a.cr_acptno       \n");
    		strQuery.append("   and a.cr_locat='00'               \n");
    		strQuery.append("   and a.cr_status='0'               \n");
    		pstmt2 = conn.prepareStatement(strQuery.toString());
    		pstmt2.setString(1, AcptNo);
    		rs = pstmt2.executeQuery();
    		while (rs.next()) {
    			strQuery.setLength(0);
            	strQuery.append("Begin CMR9900_STR ( ");
            	strQuery.append("?, ?, ?, '9', ?, '9' ); End;");
            	pstmt = conn.prepareStatement(strQuery.toString());
            	pstmt.setString(1, rs.getString("cr_acptno"));
            	pstmt.setString(2,UserId);
            	pstmt.setString(3,"선행작업 회수로 인한 자동반려처리");
            	pstmt.setString(4,rs.getString("cr_confusr"));

            	pstmt.executeUpdate();
            	pstmt.close();
    		}
    		rs.close();
    		pstmt2.close();
    		*/
        	conn.close();
			p = null;
			run = null;
			conn = null;
			pstmt = null;

        	return retmsg;

		} catch (SQLException sqlexception) {
			sqlexception.printStackTrace();
			ecamsLogger.error("## Cmr3200.reqCncl() SQLException START ##");
			ecamsLogger.error("## Error DESC : ", sqlexception);
			ecamsLogger.error("## Cmr3200.reqCncl() SQLException END ##");
			throw sqlexception;
		} catch (Exception exception) {
			exception.printStackTrace();
			ecamsLogger.error("## Cmr3200.reqCncl() Exception START ##");
			ecamsLogger.error("## Error DESC : ", exception);
			ecamsLogger.error("## Cmr3200.reqCncl() Exception END ##");
			throw exception;
		}finally{
			if (strQuery != null) 	strQuery = null;
			if (rs != null)  try{rs.close();}catch (Exception ex2){ex2.printStackTrace();}
			if (pstmt != null)  try{pstmt.close();}catch (Exception ex2){ex2.printStackTrace();}
			if (conn != null){
				try{
					conn.close();
				}catch(Exception ex3){
					ecamsLogger.error("## Cmr3200.reqCncl() connection release exception ##");
					ex3.printStackTrace();
				}
			}
		}
	}//end of reqCncl() method statement


    public String cnclYn(String AcptNo) throws SQLException, Exception {
		Connection        conn        = null;
		PreparedStatement pstmt       = null;
		PreparedStatement pstmt2      = null;
		ResultSet         rs          = null;
		ResultSet         rs2         = null;
		StringBuffer      strQuery    = new StringBuffer();
		String            retUser     = "";

		ConnectionContext connectionContext = new ConnectionResource();

		try {

			conn = connectionContext.getConnection();

        	strQuery.setLength(0);
        	strQuery.append("select count(*) as cnt from cmr9900 \n");
        	strQuery.append("where cr_acptno=? and cr_locat<>'00' \n");
        	strQuery.append("  and cr_status='9' and cr_teamcd<>'2' and cr_teamcd <> '3' \n");
        	strQuery.append("  and cr_team not in ('SYSFT','SYSPF','SYSUP','SYSCB') \n");
        	//pstmt =  new LoggableStatement(conn, strQuery.toString());
        	pstmt = conn.prepareStatement(strQuery.toString());
        	pstmt.setString(1, AcptNo);
        	//ecamsLogger.debug(((LoggableStatement)pstmt).getQueryString());
        	rs = pstmt.executeQuery();

        	if (rs.next()){
        		if (rs.getInt("cnt") == 0) {

        			strQuery.setLength(0);
        			strQuery.append("select cr_confusr from cmr9900           \n");
        			strQuery.append(" where cr_acptno=? and cr_locat='00'     \n");
        			strQuery.append("   and cr_status='0'                     \n");
        			//pstmt =  new LoggableStatement(conn, strQuery.toString());
        			pstmt2 = conn.prepareStatement(strQuery.toString());
                	pstmt2.setString(1, AcptNo);
                	//ecamsLogger.debug(((LoggableStatement)pstmt).getQueryString());
                	rs2 = pstmt2.executeQuery();
                	if (rs2.next()) {
                		retUser = rs2.getString("cr_confusr");
                	}
                	rs2.close();
                	pstmt2.close();
        		}
        	}
        	rs.close();
        	pstmt.close();

        	rs = null;
        	pstmt = null;
        	rs2 = null;
        	pstmt2 = null;
        	conn.close();
        	conn = null;

        	return retUser;


		} catch (SQLException sqlexception) {
			sqlexception.printStackTrace();
			ecamsLogger.error("## Cmr3200.cnclYn() SQLException START ##");
			ecamsLogger.error("## Error DESC : ", sqlexception);
			ecamsLogger.error("## Cmr3200.cnclYn() SQLException END ##");
			throw sqlexception;
		} catch (Exception exception) {
			exception.printStackTrace();
			ecamsLogger.error("## Cmr3200.cnclYn() Exception START ##");
			ecamsLogger.error("## Error DESC : ", exception);
			ecamsLogger.error("## Cmr3200.cnclYn() Exception END ##");
			throw exception;
		}finally{
			if (strQuery != null) 	strQuery = null;
			if (rs != null)     try{rs.close();}catch (Exception ex){ex.printStackTrace();}
			if (pstmt != null)  try{pstmt.close();}catch (Exception ex2){ex2.printStackTrace();}
			if (rs2 != null)     try{rs2.close();}catch (Exception ex){ex.printStackTrace();}
			if (pstmt2 != null)  try{pstmt2.close();}catch (Exception ex2){ex2.printStackTrace();}
			if (conn != null){
				try{
					conn.close();
				}catch(Exception ex3){
					ecamsLogger.error("## Cmr3200.cnclYn() connection release exception ##");
					ex3.printStackTrace();
				}
			}
		}
	}//end of cnclYn() method statement

    public String reqDelete(String AcptNo,String UserId,String QryCd,String ConfUsr) throws SQLException, Exception {
		Connection        conn        = null;
		PreparedStatement pstmt       = null;
		StringBuffer      strQuery    = new StringBuffer();

		ConnectionContext connectionContext = new ConnectionResource();

		try {

			conn = connectionContext.getConnection();
			conn.setAutoCommit(false);
        	strQuery.setLength(0);
        	strQuery.append("Begin CMR9900_STR ( ");
        	strQuery.append("?, ?, '삭제처리를 위한 사전작업', '9', ?, '9' ); End;");
        	pstmt = conn.prepareStatement(strQuery.toString());
        	pstmt.setString(1, AcptNo);
        	pstmt.setString(2,UserId);
        	pstmt.setString(3,ConfUsr);
        	pstmt.executeUpdate();
        	pstmt.close();

        	strQuery.setLength(0);
        	strQuery.append("delete cmr9920 where cr_acptno=?              \n");
        	pstmt = conn.prepareStatement(strQuery.toString());
        	pstmt.setString(1, AcptNo);
        	pstmt.executeUpdate();
        	pstmt.close();

        	strQuery.setLength(0);
        	strQuery.append("delete cmr9910 where cr_acptno=?              \n");
        	pstmt = conn.prepareStatement(strQuery.toString());
        	pstmt.setString(1, AcptNo);
        	pstmt.executeUpdate();
        	pstmt.close();

        	strQuery.setLength(0);
        	strQuery.append("delete cmr9900 where cr_acptno=?              \n");
        	pstmt = conn.prepareStatement(strQuery.toString());
        	pstmt.setString(1, AcptNo);
        	pstmt.executeUpdate();
        	pstmt.close();

        	strQuery.setLength(0);
        	strQuery.append("delete cmr1001 where cr_acptno=?              \n");
        	pstmt = conn.prepareStatement(strQuery.toString());
        	pstmt.setString(1, AcptNo);
        	pstmt.executeUpdate();
        	pstmt.close();

        	if (QryCd.substring(0,1).equals("3")) {
        		strQuery.setLength(0);
            	strQuery.append("delete cmr1100 where cr_acptno=?              \n");
            	pstmt = conn.prepareStatement(strQuery.toString());
            	pstmt.setString(1, AcptNo);
            	pstmt.executeUpdate();
            	pstmt.close();

        		strQuery.setLength(0);
            	strQuery.append("delete cmr1002 where cr_acptno=?              \n");
            	pstmt = conn.prepareStatement(strQuery.toString());
            	pstmt.setString(1, AcptNo);
            	pstmt.executeUpdate();
            	pstmt.close();
        	} else {
        		strQuery.setLength(0);
	        	strQuery.append("delete cmr1011 where cr_acptno=?              \n");
	        	pstmt = conn.prepareStatement(strQuery.toString());
	        	pstmt.setString(1, AcptNo);
	        	pstmt.executeUpdate();
	        	pstmt.close();

        		strQuery.setLength(0);
	        	strQuery.append("delete cmr1030 where cr_acptno=?              \n");
	        	pstmt = conn.prepareStatement(strQuery.toString());
	        	pstmt.setString(1, AcptNo);
	        	pstmt.executeUpdate();
	        	pstmt.close();

        		strQuery.setLength(0);
	        	strQuery.append("delete cmr1030 where cr_befact=?              \n");
	        	pstmt = conn.prepareStatement(strQuery.toString());
	        	pstmt.setString(1, AcptNo);
	        	pstmt.executeUpdate();
	        	pstmt.close();

        		strQuery.setLength(0);
	        	strQuery.append("delete cmr0027 where cr_acptno=?              \n");
	        	pstmt = conn.prepareStatement(strQuery.toString());
	        	pstmt.setString(1, AcptNo);
	        	pstmt.executeUpdate();
	        	pstmt.close();

        		strQuery.setLength(0);
	        	strQuery.append("delete cmr1010 where cr_acptno=?              \n");
	        	pstmt = conn.prepareStatement(strQuery.toString());
	        	pstmt.setString(1, AcptNo);
	        	pstmt.executeUpdate();
	        	pstmt.close();
        	}
        	strQuery.setLength(0);
        	strQuery.append("delete cmr1000 where cr_acptno=?              \n");
        	pstmt = conn.prepareStatement(strQuery.toString());
        	pstmt.setString(1, AcptNo);
        	pstmt.executeUpdate();
        	pstmt.close();
        	conn.commit();
        	conn.close();
			conn = null;
			pstmt = null;

        	return "0";

		} catch (SQLException sqlexception) {
			sqlexception.printStackTrace();
			ecamsLogger.error("## Cmr3200.reqDelete() SQLException START ##");
			ecamsLogger.error("## Error DESC : ", sqlexception);
			ecamsLogger.error("## Cmr3200.reqDelete() SQLException END ##");
			if (conn != null) conn.rollback();
			throw sqlexception;
		} catch (Exception exception) {
			exception.printStackTrace();
			ecamsLogger.error("## Cmr3200.reqDelete() Exception START ##");
			ecamsLogger.error("## Error DESC : ", exception);
			ecamsLogger.error("## Cmr3200.reqDelete() Exception END ##");
			if (conn != null) conn.rollback();
			throw exception;
		}finally{
			if (strQuery != null) 	strQuery = null;
			if (pstmt != null)  try{pstmt.close();}catch (Exception ex2){ex2.printStackTrace();}
			if (conn != null){
				try{
					conn.close();
				}catch(Exception ex3){
					ecamsLogger.error("## Cmr3200.reqDelete() connection release exception ##");
					ex3.printStackTrace();
				}
			}
		}
	}//end of reqDelete() method statement

}//end of Cmr3200 class statement
