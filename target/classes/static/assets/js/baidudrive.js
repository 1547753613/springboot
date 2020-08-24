/*
注意：
添加新的js时请使用命名空间来管理。
*/
var Airportstr = "";
var Trainstr = "";
function MuniaoMapDriving() {
    this.num = 1;
    this.pointArray = [];
    this.searchPoi = {};
    this.searchPoint = [];
    this.localSearchCache = {
        'jt': {}
    };
    this.config = { '机场': '/Content/images/jichang.png', '火车站': '/Content/images/ditie.png', '地铁站': '/Content/images/ditie.png' };
    this.map;
    this.local;
    this.drivingRoute;
    window.localSearchCache = this.localSearchCache;
}
MuniaoMapDriving.prototype.getIcon = function (src) {
    var markerImg = src;
    var myIcon = new BMap.Icon(markerImg, new BMap.Size(40, 50));
    myIcon.setImageSize(new BMap.Size(40, 50));
    return myIcon;
};
MuniaoMapDriving.prototype.addMarker = function (point, src, i, pointMessage, num, index) {
    var label, marker;
    marker = new BMap.Marker(point);
    marker.setIcon(this.getIcon(src));
    this.map.addOverlay(marker);
    this.pointArray.push(point);
    /*绑定事件*/
    if (pointMessage) {
        num = num ? num : "";
        label = new BMap.Label("<div class='hover-label' id='label_" + index + "'><p class='label-name'><span>" + num + "</span>" + pointMessage.title + "</p><p class='label-address'>" + pointMessage.address + "</p></div>", { offset: new BMap.Size(12, -44) });
        marker.setLabel(label);
        addMouseoverHandler(index, marker, this);
    }
    if (i == 0) {
        marker.setTop(true);
    }
};
MuniaoMapDriving.prototype.createSearchHtml = function (results, lat, lng, roomid) {
    var _this = this;
    this.map.clearOverlays();
    //缓存keyword
    this.local.keyword = $.isArray(results) ? '交通' : results.keyword;
    if ($.isArray(results)) {
        this.localSearchForJT(results);
        this.Getresultstr(results, lat, lng, roomid);
    }
};
MuniaoMapDriving.prototype.localSearchForJT = function (results) {
    for (var i = 0; i < results.length ; i++) {
        var result = results[i];
        var title = result.keyword;
        if (result.getNumPois() > 0) {
            switch (title) {
                case '机场':
                    var a = [];
                    a.push(result.getPoi(0));
                    result.getPoi(1) && a.push(result.getPoi(1));
                    if (a.length > 0) {
                        localSearchCache['jt']['0'] = {
                            'keyword': title,
                            'result': a
                        };
                    }
                    break;
                case '火车站':
                    var b = [];
                    b.push(result.getPoi(0));
                    result.getPoi(1) && b.push(result.getPoi(1));
                    result.getPoi(2) && b.push(result.getPoi(2));
                    result.getPoi(3) && b.push(result.getPoi(3));
                    result.getPoi(4) && b.push(result.getPoi(4));
                    if (b.length > 0) {
                        localSearchCache['jt']['1'] = {
                            'keyword': title,
                            'result': b
                        };
                    }
                    break;
                case '地铁站':
                    localSearchCache['jt']['2'] = result;
                    break;
            }
        }
    }
};
MuniaoMapDriving.prototype.Getresultstr = function (results, lat, lng, roomid) {
    var arry = [];
    for (var i in localSearchCache['jt']) {
        result = localSearchCache['jt'][i];
        title = result.keyword;
        var resultLen = result.result ? result.result.length : result.getCurrentNumPois();
        for (var j = 0; j < resultLen; j++) {
            var type = 0;
            var poi = result.result ? result.result[j] : result.getPoi(j);
            if (title == "机场") {
                type = 0;
            } else if (title == "火车站") {
                type = 1;
            } else if (title == "地铁站") {
                type = 2;
            }
            if (arry.length&&arry[0].poitype == 0 && type == 2) {
                continue;
            }
            arry.push({
                poitype: type, poilat: poi.point.lat, poilng: poi.point.lng, poiname: poi.title, poiroomid: roomid, roomlat: lat, roomlng: lng
            });
            
        }
    }
    this.Getdrivestr(arry, lat, lng);
};
MuniaoMapDriving.prototype.Getdrivestr = function (resultstr, lat, lng) {
    var maps = new BMap.Map("allmaps");
    var roompoint = new BMap.Point(lng, lat);
    for (var i in resultstr) {
        var result = resultstr[i];
        var point = new BMap.Point(result.poilng, result.poilat);
        (function (result, point) {
            var SearchComplete = function (results) {
                if (transit.getStatus() != BMAP_STATUS_SUCCESS) {
                    return;
                }
                var plan = results.getPlan(0);
                result.poitime = plan.getDuration(true);// 获取时间
                result.poidistance = plan.getDistance(true);//获取距离
            }
            var transit = new BMap.DrivingRoute(maps, {
                renderOptions: { map: maps },
                onSearchComplete: SearchComplete
            });
            transit.search(point, roompoint);
        })(result, point);
    }
    this.AddDrivingRoute(resultstr);
};
MuniaoMapDriving.prototype.AddDrivingRoute = function (resultstr) {
    setTimeout(function () {
        //console.log(JSON.stringify(resultstr));
        $.ajax({
            type: "post"
           , url: "/Home/AddDrivingRoute"
           , data: { postdate: JSON.stringify(resultstr) }
           , dataType: "json"
           , error: function () { }
           , success: function (d) {

           }
        });
    }, "9000");
}
MuniaoMapDriving.prototype.bMapInit = function () {
    /*创建地图*/
    var _this = this,
        lng = $("#roommap").attr("lng"),
        lat = $("#roommap").attr("lat"),
        roomid = $("#roomid").val(),
        point;
    this.map = new BMap.Map("roommap", { enableMapClick: false });
    this.map.disableScrollWheelZoom();
    this.map.addControl(new BMap.NavigationControl());
    this.map.addControl(new BMap.ScaleControl({ anchor: BMAP_ANCHOR_BOTTOM_LEFT }));
    point = new BMap.Point(lng, lat);
    this.addMarker(point, "/Content/images/muniao.png", 0);
    var zoom = { zoomFactor: this.pointArray.length > 2 ? 0 : -3, margins: [10, 10, 10, 10] };
    this.map.setViewport(this.pointArray, zoom);
    //周边
    this.local = new BMap.LocalSearch(this.map, {
        renderOptions: { autoViewport: true, selectFirstResult: false },
        pageCapacity: 12,
        onSearchComplete: function (results) {
            if (_this.num < 3) {
                var ret = [];
                var datas = [];
                for (var j = 0; j < results.length; j++) {
                    ret = results[j];
                    var iconTag = _this.config[ret.keyword];
                    var wr = ret.ur || ret.mr || ret.or || ret.wr || ret.xr || ret.vr || ret.zr;
                    if (!wr)
                        continue;
                    for (var i = 0; i < wr.length; i++) {
                        var data = wr[i];
                        if (i == 8) break;
                        data.iconTag = iconTag;
                        datas.push(data);
                    }
                }
                _this.num++;
            }
            //根据结构返回数据构建html
            _this.createSearchHtml(results, lat, lng, roomid);
        }
    });
    //初始化默认显示交通
    this.local.search(['机场', '火车站']);
    this.local.searchNearby(['地铁站'],point, 2000);
};