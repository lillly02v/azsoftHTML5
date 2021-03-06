(function() {
    SBUxG.DEF.MSG = {
        // LICENSE
        NOT_FOUND_LICENSE: '[SBUx] 라이센스가 등록되지 않았습니다.',
        INVALID_LICENSE: '[SBUx] 라이센스가 유효하지 않습니다.',
        EXPIRED_LICENSE: '[SBUx] 라이센스 사용기간이 만료되었습니다.',

        // COMMON
        NOT_DEV_API: '[SBUx] SBUxMethod.${attr} API 는 미개발된 기능입니다.',
        NOT_DEV_FUNC: '[SBUx] ${attr} 컴포넌트의 ${attr1} 는 미개발된 기능입니다.',
        NOT_FOUND_TAG: '[SBUx] ${attr} 명인 태그가 존재하지 않습니다.',
        NOT_ENTERED_INVALID_TAG: '[SBUx] ${attr} 태그명이 잘못되었습니다.',
        NOT_ENTERED_ATTR: '[SBUx] ${attr} 속성이 입력되지 않았습니다.',
        NOT_PROVIDE_ATTR: '[SBUx] ${attr} 속성의 기능은 제공하지 않습니다.',
        NOT_PROVIDE_ATTR_ATTR: '[SBUx] ${attr} 에서 <br> ${attr1} 는 <br>제공하지 않습니다.',
        NOT_PROVIDE_FUNC: '[SBUx] ${attr} 을 바로 사용할 수 없습니다.',
        NOT_ENTERED_ID: '[SBUx] id 가 입력되지 않았습니다.',
        NOT_ENTERED_NAME: '[SBUx] name 이 입력되지 않았습니다.',
        NOT_ENTERED_UITYPE: '[SBUx] uitype 이 입력되지 않았습니다.',
        NOT_ENTERED_MODE: '[SBUx] mode 가 입력되지 않았습니다.',
        NOT_ENTERED_REQ_ATTRI: '[SBUx] 필수 속성이 입력되지 않았습니다.',
        NOT_EXIST_FORMNAME: '[SBUx] Form Node 에 대한 name 이 있어야 합니다.',
        NOT_EXIST_FORMID: '[SBUx] Form Node 에 대한 id 가 있어야 합니다.',
        OVER_PARAMS: '[SBUx] 파라메터 개수는 최대 10개까지 지원합니다.',
        INVALID_UITYPEVALUE: '[SBUx] 잘못된 uitype 속성 값이 입력되었습니다. 철자를 확인해 주세요.',
        INVALID_ATTRIVALUE: '[SBUx] 잘못된 속성 값이 입력되었습니다. 철자를 확인해 주세요.',
        INVALID_ATTRIVALUE_COUNT: '[SBUx] 부족한 속성값이 입력되었습니다.',
        INVALID_ATTRIVALUE_SPACE: '[SBUx] 공백이거나 스페이스는 허용하지 않습니다.',
        INVALID_ATTRIVALUE_UNDEFINED: '[SBUx] Undefined 는 허용하지 않습니다.',
        MUST_ATTRIVALUE : '[SBUx] ${attr} 속성값으로 설정되어 있어야 합니다.',

        NOT_FOUND_TEXT: '검색된 결과가 없습니다.',

        // MESSAGE
        MESSAGE_NOT_FOUND_NAME: '[SBUx] 참조할 수 있는 name 이 없습니다.',
        MESSAGE_NOT_FOUND_ID: '[SBUx] 참조할 수 있는 id 가 없습니다.',

        // VALIDATE
        MESSAGE_ONLY_NUMBER: '[SBUx] 숫자가 입력되어야 합니다.',
        MESSAGE_ONLY_STRING: '[SBUx] 문자가 입력되어야 합니다.',
        MESSAGE_ONLY_BOOLEAN: '[SBUx] true 또는 false 가 입력되어야 합니다.',
        MESSAGE_ONLY_PHONE_NUMBER: '[SBUx] 전화번호는 숫자만 입력하셔야 합니다.',
        MESSAGE_NOT_ALLOWED_RANGE: '[SBUx] 허용된 범위를 벗어났습니다.',
        MESSAGE_NOT_ALLOWED_DIGIT: '[SBUx] 소수점은 허용치 않습니다.',
        MESSAGE_NOT_ALLOWED_MINUS: '[SBUx] 마이너스값은 허용치 않습니다.',
        MESSAGE_NOT_ALLOWED_STRING: '[SBUx] 문자는 허용치 않습니다.',
        MESSAGE_NOT_ALLOWED_SPACE: '[SBUx] 공백은 허용치 않습니다.',

        // RADIO
        RADIO_NO_DATA: '미지정',

        // CHECKBOX
        CHECKBOX_NO_DATA: '미지정',

        // SELECT BOX
        SELECT_NO_DATA: '-없음-',
        SELECT_UNSELECTED: '-선택하세요-',
        IS_SELECT_ALL_TEXT: '전체 선택',
        TITLE_SELECT_MAX_TEXT: '개 선택됨',
        TITLE_SELECT_ALL_TEXT: '전체 선택됨',

        // LISTBOX
        LISTBOX_NO_DATA: '데이터가 존재하지 않습니다.',

        // TREE
        SELECT_NODE_NEED_TITLE: 'Tree Info',
        SELECT_NODE_NEED: '노드가 선택되지 않았습니다',
        INVALID_NODE_INFO :'올바르지 않은 노드 정보입니다.',
        AVAILABLE_JSONDATA_TYPE: 'JsonData 형태만 가능합니다',
        NEW_TREE_NODE_TEXT: '신규 노드',
        NEW_TREE_NODE_CREATION_FAILED : '노드 생성에 실패하였습니다.',
        NEW_TREE_NODE_VALUE: '',
        INFO_DELETE_CHILD_NODE: '하위노드도 함께 삭제됩니다.',
        INFO_DELETE_NODE: '노드를 삭제합니다.',
        NODE_DUPLICATION_KEY: '노드의 중복키가 발생하였습니다.제품사에 문의바랍니다.',
        TREE_NO_DATA : '데이터가 존재하지 않습니다.',

        // ALERT
        CONFIRM_OK: '확인',
        CONFIRM_CANCEL: '취소',

        // DROPDOWN TEXT
        DROPDOWN_TEXT: '선택하세요',

        // COLSE TEXT
        CLOSE_TEXT: '닫기',

        // TAB TEXT
        TAB_DONT_CLOSE : '다른 탭이 모두 비활성 상태입니다.',
        TAB_OPEN_NOTFOUND_LINK : '연결된 화면이 존재하지 않습니다.',
        TAB_DONT_REMOVE : '삭제할 탭이 존재하지 않습니다.',

        // PROGRESS BAR
        PROGRESS_NOT_ALLOWED_TWOBAR : '"indicator-type" 속성이 "normal" 값인 경우는 sbux-bar tag 를 하나만 설정하셔야 합니다.',
        PROGRESS_LOADING_TEXT : 'LOADING',

        // CONTEXT MENU
        CTXT_MENU_DATA_COLLECT : '[SBUx] Child nodes 의 데이터 취합에서 오류가 발생하였습니다.',
        CTXT_MENU_INVALID_SELECTOR : '[SBUx] ${attr} 의 Selector 지정을 확인하시기 바랍니다.',

        // SBGRID
        NOT_ENTERED_COLUMS: '[SBUx] 참조할 수 있는 컬럼정보가 없습니다.',
        NOT_ENTERED_STYLE : '[SBUx] 그리드의 너비와 폭을 정하는 스타일 정보가 없습니다.',
        NO_ROWS_SELECTED : '[SBUx] 입력될 데이터의 행을 선택하셔야 합니다.',

        // Required
        IE9_NOT_ENTERED_REQ_COMMON: '필수사항입니다.',
        IE9_NOT_ENTERED_REQ_INPUT: '이 입력란을 작성하세요.',
        IE9_NOT_ENTERED_REQ_SELECT: '목록에서 항목을 선택하세요.',
        IE9_NOT_ENTERED_REQ_CHECK: '이 확인란을 선택해야 합니다.',
        IE9_NOT_ENTERED_REQ_RADIO: '옵션을 선택해야 합니다.',
        IE9_NOT_ENTERED_REQ_PICKER: '일자를 선택하세요.',
        IE9_NOT_ENTERED_REQ_DROPDOWN: '목록에서 항목을 선택하세요.',

        // Minlength
        IE_MINLENGTH_REQ_INPUT : '이 텍스트를 *자 이상으로 늘리세요(현재 # 자 사용중).',

        // DATA STORE
        DATA_RECEIVE_LOADING_TITLE: '데이터 로딩중',
        DATA_RECEIVE_LOADING_TEXT: '잠시만 기다려 주세요',
        DATA_RECEIVE_ERROR_TITLE: '데이터 로딩 에러',
        DATA_RECEIVE_ERROR_TEXT: '세션이 종료되었거나 네트워크 문제가 발생하였습니다.',
        DATA_SEND_JSONDATA_ERROR: '[SBUx] 문법오류가 있는 Send Data 입니다.',
        DATA_NETWORK_ERROR_TITLE: '네크워크 연결 에러',
        DATA_NETWORK_ERROR_TEXT: '네크워크 연결에 문제가 발생하였습니다.',

        // COMMON ERROR_MSG
        JSONDATA_MUST_HAVE: 'jsondata-ref 속성은 필수입니다.',
        JSONDATA_NOT_FOUND: 'jsondata-ref 에 매핑된 객체를 찾을 수 없습니다. 배열형태의 객체여야 합니다.',
        JSONDATA_ALREADY_CHANGED: 'json 형태로 이미 변경되어 있습니다. datastore-data-type="json" 속성을 제거하여 주십시오.',

        // SBGrid
        SBGRID21_NOT_IMPORT: '[SBUx] SBGrid 2.1 파일을 Load 하지 못하였습니다. SBUxConfig 설정에서 SBGrid : { Version2_1 : true } 를 추가하십시오.',
        SBGRID25_NOT_IMPORT: '[SBUx] SBGrid 2.5 파일을 Load 하지 못하였습니다. SBUxConfig 설정에서 SBGrid : { Version2_5 : true } 를 추가하십시오.',

        // Carousel
        CAROUSEL_NOT_FOUND : '표시할 슬라이드가 없습니다.',

        // Side menu
        SIDEMENU_NO_DATA : '데이터가 존재하지 않습니다.',

        // web accessibility
        WEB_ACCESS_UNTITLED : '미정',

        // localstorage
        TO_USE_LOCALSTORAGE : '[SBUx] SBUxConfig 설정에서 SystemLogType : "storage" 또는 DeveloperTipType : "storage" 를 사용하려면, 주소 표시 줄에서 http 또는 https로 시작해야 합니다. 시스템이 동작하기 위해 "콘솔" 유형으로 변경합니다.'
    };

    SBUxG.DEF.MSG_KOR = {
        RESIDENT_WRONG_NUMERIC: '주민등록번호를 숫자로 입력하세요',
        RESIDENT_WRONG_FRONT_NUMERIC: '주민등록번호 앞자리를 확인하세요.',
        RESIDENT_WRONG_NUMERIC_DIGIT: '주민등록번호 자릿수를 확인하세요.',
        RESIDENT_WRONG: '주민등록번호를 확인하고 다시 입력하세요'
    };
}());