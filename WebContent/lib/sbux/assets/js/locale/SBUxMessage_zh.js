(function() {
    SBUxG.DEF.MSG = {
        // LICENSE
        NOT_FOUND_LICENSE: '[SBUx] 许可证未注册。',
        INVALID_LICENSE: '[SBUx] 许可证无效。',
        EXPIRED_LICENSE: '[SBUx] 此许可证已过期。',

        // COMMON
        NOT_DEV_API: '[SBUx] SBUxMethod.${attr} API 是一个未开发的功能。',
        NOT_DEV_FUNC: '[SBUx] ${attr} 组件的 ${attr1} 是一个未开发的功能。',
        NOT_FOUND_TAG: '[SBUx] 未找到标签名称，错误的标签名称：${attr}',
        NOT_ENTERED_INVALID_TAG: '[SBUx] 请输入正确的标签名称，错误的标签名称：${attr}',
        NOT_ENTERED_ATTR: '[SBUx] ${attr} 尚未输入',
        NOT_PROVIDE_ATTR: '[SBUx] ${attr} 它不提供此属性的功能。',
        NOT_PROVIDE_ATTR_ATTR: '[SBUx] ${attr1}, ${attr} 它不提供此属性的功能。',
        NOT_PROVIDE_FUNC: '[SBUx] ${attr} 不是立即可用的。',
        NOT_ENTERED_ID: '[SBUx] 编号尚未输入。',
        NOT_ENTERED_NAME: '[SBUx] 没有输入的名称。',
        NOT_ENTERED_UITYPE: '[SBUx] uitype尚未输入',
        NOT_ENTERED_MODE: '[SBUx] 模式尚未输入。',
        NOT_ENTERED_REQ_ATTRI: '[SBUx] 必需属性没有输入。',
        NOT_EXIST_FORMNAME: '[SBUx] 您必须为窗体节点的名称。',
        NOT_EXIST_FORMID: '[SBUx] 它必须对表节点ID。',
        OVER_PARAMS: '[SBUx] 参数号支持最多10个。',
        INVALID_UITYPEVALUE: '[SBUx] Uitype不正确的属性值已被输入。请检查您的拼写。',
        INVALID_ATTRIVALUE: '[SBUx] 无效的属性值输入。请检查您的拼写。',
        INVALID_ATTRIVALUE_COUNT: '[SBUx] 缺少属性值被输入。',
        INVALID_ATTRIVALUE_SPACE: '[SBUx] 空间或不允许有空格。',
        INVALID_ATTRIVALUE_UNDEFINED: '[SBUx] 未定义是不允许的。',
        MUST_ATTRIVALUE : '[SBUx] 必須設置屬性值。[${attr}]',

        NOT_FOUND_TEXT: '未找到结果。',

        // MESSAGE
        MESSAGE_NOT_FOUND_NAME: '[SBUx] 没有可引用的名称。',
        MESSAGE_NOT_FOUND_ID: '[SBUx] 没有ID可被引用。',

        // VALIDATE
        MESSAGE_ONLY_NUMBER: '[SBUx] 数量必须输入。',
        MESSAGE_ONLY_STRING: '[SBUx] 字符必须输入。',
        MESSAGE_ONLY_BOOLEAN: '[SBUx] 真的还是假的，必须输入。',
        MESSAGE_ONLY_PHONE_NUMBER: '[SBUx] 电话号码只能是数字',
        MESSAGE_NOT_ALLOWED_RANGE: '[SBUx] 外可接受的范围。',
        MESSAGE_NOT_ALLOWED_DIGIT: '[SBUx] 小数点是不允许的。',
        MESSAGE_NOT_ALLOWED_MINUS: '[SBUx] 负值是不允许的。',
        MESSAGE_NOT_ALLOWED_STRING: '[SBUx] 字符是不允许的。',
        MESSAGE_NOT_ALLOWED_SPACE: '[SBUx] 不允许有空格。',

        // RADIO
        RADIO_NO_DATA: '未指定',

        // CHECKBOX
        CHECKBOX_NO_DATA: '未指定',

        // SELECT BOX
        SELECT_NO_DATA: '-没有数据-',
        SELECT_UNSELECTED: '-请选择-',
        IS_SELECT_ALL_TEXT: '全选',
        TITLE_SELECT_MAX_TEXT: ' 选',
        TITLE_SELECT_ALL_TEXT: '所有选定',

        // LISTBOX
        LISTBOX_NO_DATA: '',

        // TREE
        SELECT_NODE_NEED_TITLE: 'Tree Info',
        SELECT_NODE_NEED: '所选节点是必需的。',
        INVALID_NODE_INFO: '无效的节点信息。',
        AVAILABLE_JSONDATA_TYPE: '它只能形成JsonData',
        NEW_TREE_NODE_TEXT: '新节点',
        NEW_TREE_NODE_CREATION_FAILED : '节点创建失败',
        NEW_TREE_NODE_VALUE: '',
        INFO_DELETE_CHILD_NODE: '子节点也将被删除',
        INFO_DELETE_NODE: '删除节点。',
        NODE_DUPLICATION_KEY: '重复的关键节点已经发生。请联系产品公司',
        TREE_NO_DATA : '没有数据',

        // ALERT
        CONFIRM_OK: '确认',
        CONFIRM_CANCEL: '取消',

        // DROPDOWN TEXT
        DROPDOWN_TEXT: '-请选择-',

        // COLSE TEXT
        CLOSE_TEXT: '关闭',

        // TAB TEXT
        TAB_DONT_CLOSE : '所有其他选项卡都是不活动',
        TAB_OPEN_NOTFOUND_LINK : '连接的屏幕不存在。',
        TAB_DONT_REMOVE : '要删除的选项卡不存在。',

        // PROGRESS BAR
        PROGRESS_NOT_ALLOWED_TWOBAR : '如果 indicator-type="normal"，则只能设置一个 sbux-bar 标签',
        PROGRESS_LOADING_TEXT : 'LOADING',

        // CONTEXT MENU
        CTXT_MENU_DATA_COLLECT : '[SBUx] 收集来自子节点的数据时出错。',
        CTXT_MENU_INVALID_SELECTOR : '[SBUx] 检查${attr}中的选择器。',

        // SBGRID
        NOT_ENTERED_COLUMS: '[SBUx] 没有列信息看。',
        NOT_ENTERED_STYLE : '[SBUx] 没有样式信息来确定网格的宽度和高度。',
        NO_ROWS_SELECTED : '[SBUx] 您必须在网格中选择一行。',

        // IE9 Required
        IE9_NOT_ENTERED_REQ_COMMON: '這是必需的。',
        IE9_NOT_ENTERED_REQ_INPUT: '请填写此字段。',
        IE9_NOT_ENTERED_REQ_SELECT: '请从列表中选择一个项目。',
        IE9_NOT_ENTERED_REQ_CHECK: '必要的选择。',
        IE9_NOT_ENTERED_REQ_RADIO: '必要的选择。',
        IE9_NOT_ENTERED_REQ_PICKER: '请选择一个日期。',
        IE9_NOT_ENTERED_REQ_DROPDOWN: '请从列表中选择一个项目。',

        // Minlength
        IE_MINLENGTH_REQ_INPUT : '请增加此文字至少*个字符（目前使用#个字符）。',

        // DATA STORE
        DATA_RECEIVE_LOADING_TITLE: '加载数据',
        DATA_RECEIVE_LOADING_TEXT: '请稍候',
        DATA_RECEIVE_ERROR_TITLE: '数据加载错误',
        DATA_RECEIVE_ERROR_TEXT: '请检查您的网络连接状态',
        DATA_SEND_JSONDATA_ERROR: '[SBUx] 这是一个语法错误发送数据。',
        DATA_NETWORK_ERROR_TITLE: '网络连接错误。',
        DATA_NETWORK_ERROR_TEXT: '发生在网络连接上的问题。',

        // COMMON ERROR_MSG
        JSONDATA_MUST_HAVE: '该 jsondata-ref 属性是必需的。',
        JSONDATA_NOT_FOUND: '无法找到与 jsondata-ref 关联的对象。',
        JSONDATA_ALREADY_CHANGED: '目前已经有JSON格式的变化。请删除属性，数据存储 - 数据类型=“ JSON ”',

        // SBGrid
        SBGRID21_NOT_IMPORT: '[SBUx] SBGrid 2.1文件未找到。请更改为SBGrid : { Version2_1 : true } 在SBUxConfig设置。',
        SBGRID25_NOT_IMPORT: '[SBUx] SBGrid 2.5文件未找到。请更改为SBGrid : { Version2_5 : true } 在SBUxConfig设置。',

        // Carousel
        CAROUSEL_NOT_FOUND : '没有要显示的幻灯片。',

        // Side menu
        SIDEMENU_NO_DATA : '没有数据',

        // web accessibility
        WEB_ACCESS_UNTITLED : '无标题',

        // localstorage
        TO_USE_LOCALSTORAGE : '要在SBUxConfig设置中使用SystemLogType："storage"或DeveloperTipType："storage"， 您必须在地址栏中以http或https开头。 更改为 "console" 类型以使系统正常工作。'
    };

}());