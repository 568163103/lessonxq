$(function () {
    // 配置相关
    var statusArr = [
        { value: 0, color: 'green', colorH: '绿', text: '畅通' },
        { value: 1, color: '#FFD700', colorH: '黄', text: '轻度拥堵' },
        { value: 2, color: '#FF7F00', colorH: '橙', text: '中度拥堵' },
        { value: 3, color: 'red', colorH: '红', text: '严重拥堵' }
    ]

    // 预警位置
    var posArr = [
        { code: 1, text: "一号线", detectID: "012,015,016" },
        { code: 5, text: "五号线", detectID: "013,014,017,020" }
    ]

    // ajax的url前半段
    var ajaxUrl = 'http://47.93.42.216:9001'

    // 配置uniqueid、名称、图片
    var control = {
        home: { uniqueId: '0101' },
        prediction: [
            {
                uniqueId: '0201',
                text: '1号线站台',
                src: 'images/yangqi/1/1site.jpg'
            },
            {
                uniqueId: '0202',
                text: '1号线站厅',
                src: 'images/yangqi/1/1room.jpg'
            },
            {
                uniqueId: '0203',
                text: '5号线站台',
                src: 'images/yangqi/1/5site.jpg'
            },
            {
                uniqueId: '0204',
                text: '5号线站厅',
                src: 'images/yangqi/1/5room.jpg'
            }
        ],
        forecast: [
            {
                uniqueId: '0301',
                text: '1号线站台',
                src: 'images/yangqi/2/1site.jpg'
            },
            {
                uniqueId: '0302',
                text: '1号线站厅',
                src: 'images/yangqi/2/1room.jpg'
            },
            {
                uniqueId: '0303',
                text: '5号线站台',
                src: 'images/yangqi/2/5site.jpg'
            },
            {
                uniqueId: '0304',
                text: '5号线站厅',
                src: 'images/yangqi/2/5room.jpg'
            }
        ],
        evaluation: [
            {
                uniqueId: '0401',
                text: '1号线站台',
                src: 'images/yangqi/3/1site.jpg'
            },
            {
                uniqueId: '0402',
                text: '1号线站厅',
                src: 'images/yangqi/3/1room.jpg'
            },
            {
                uniqueId: '0403',
                text: '5号线站台',
                src: 'images/yangqi/3/5site.jpg'
            },
            {
                uniqueId: '0404',
                text: '5号线站厅',
                src: 'images/yangqi/3/5room.jpg'
            }
        ]
    }

    window['statusArr'] = statusArr
    window['posArr'] = posArr
    window['ajaxUrl'] = ajaxUrl
    window['control'] = control

})

