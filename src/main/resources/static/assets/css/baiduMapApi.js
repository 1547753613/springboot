/*
注意：
添加新的js时请使用命名空间来管理。
*/
function MuniaoMap() {
    this.num = 1;
    this.pointArray = [];
    this.searchPoi = {};
    this.searchPoint = [];
    this.localSearchCache = {
        'cy': {},
        'jt': {},
        'jd': {},
        'gw': {},
        'yl': {}
    };
    this.localSearchKeywordsMap = {
        'cy': '餐饮',
        'jt': ['机场', '火车站', '长途汽车站', '公交', '地铁站'],
        'jd': '景点',
        'gw': '购物',
        'yl': '娱乐'
    };
    this.config = { '机场': '/Content/images/jichang.png', '火车站': '/Content/images/ditie.png', '长途汽车站': '/Content/images/gingjiao.png', '地铁站': '/Content/images/ditie.png', '餐饮': '/Content/images/canyin.png', '景点': '/Content/images/jingdian.png', '购物': '/Content/images/chaoshi.png', '娱乐': '/Content/images/yule.png' };
    this.map;
    this.walkingRoute;
    this.drivingRoute;
    this.busRoute;
    this.startPoint;
    this.endPoint;
    this.routeMethod;
    this.jtSeacrhFlag = 0;
    this.pageIndex = 0;
    this.pageTotal;
    this.local;
    window.localSearchCache = this.localSearchCache;
}
MuniaoMap.prototype.getIcon = function (src) {
    var markerImg = src;
    var myIcon = new BMap.Icon(markerImg, new BMap.Size(40, 50));
    myIcon.setImageSize(new BMap.Size(40, 50));
    return myIcon;
};
MuniaoMap.prototype.getKey = function (value) {
    var key;
    switch (value) {
        case '餐饮':
            key = 'cy';
            break;
        case '交通':
            key = 'jt';
            break;
        case '景点':
            key = 'jd';
            break;
        case '购物':
            key = 'gw';
            break;
        case '娱乐':
            key = 'yl';
            break;
    }
    return key;
};
MuniaoMap.prototype.routeByMethod = function (method) {
    this.routeMethod = method;
    switch (method) {
        case 'bus':
            this.busRoute.search(this.startPoint, this.endPoint);
            break;
        case 'drive':
            this.drivingRoute.search(this.startPoint, this.endPoint);
            break;
        case 'walk':
            this.walkingRoute.search(this.startPoint, this.endPoint);
            break;
    }
};
MuniaoMap.prototype.setUIStatus = function (status, dom, tip) {
    var noTip = tip ? tip : '抱歉，木鸟没有找到相关的信息。';
    switch (status) {
        case 0:
            $('#map_resultList').hide();
            if (dom) {
                dom.html('<div class="tips">木鸟正在努力加载中…</div>').show();
                $('#routeContent').show();
            } else {
                $('#routeContent').hide();
                $('.room-mapTips').html('木鸟正在努力加载中…').show();
            }
            break;
        case 1:
            $('.room-mapTips').hide();
            $('#map_resultList').show();
            $('#routeContent').hide();
            break;
        case 2:
            $('.room-mapTips').hide();
            $('#map_resultList').hide();
            $('#routeContent').show();
            break;
        case 3:
            $('#map_resultList').hide();
            if (dom) {
                dom.html('<div class="tips">' + noTip + '</div>').show();
            } else {
                $('#routeContent').hide();
                $('.room-mapTips').html(noTip).show();
            }
            break;
    }
};
MuniaoMap.prototype.addMarker = function (point, src, i, pointMessage, num, index) {
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
MuniaoMap.prototype.createSearchHtml = function (results) {
    var _this = this,
        html = "",
        p = "",
        count = 0,
        src;
    html += "<h4>" + results.keyword + "</h4><ul>";
    this.map.clearOverlays();
    this.searchPoint = [];
    this.searchPoi = {};
    //缓存keyword
    this.local.keyword = $.isArray(results) ? '交通' : results.keyword;
    if ($.isArray(results)) {
        if (this.jtSeacrhFlag == 0) {
            this.localSearchForJT(results);
            this.jtSeacrhFlag++;
        } else {
            this.localSearchForJT(results);
            this.createJTHtml(results);
            this.jtSeacrhFlag = 0;
        }
        return;
    }
    src = this.config[results.keyword];
    count = results.getCurrentNumPois();

    if (count === 0) {
        this.setUIStatus(3);
    } else {
        for (var i = 0; i < count - 1; i++) {
            this.searchPoint.push(results.getPoi(i).point);
            if (!this.searchPoi[src]) {
                this.searchPoi[src] = [];
            }
            this.searchPoi[src].push(results.getPoi(i));
            var num = i + 1;
            num = (num > 9 ? num : "0" + num);
            html += '<li class="map-search-list" method="route" lat="' + results.getPoi(i).point.lat + '" lng="' + results.getPoi(i).point.lng + '" title="' + results.getPoi(i).title + '"><span class="result-orderNum">' + num + '</span><span class="search-list-title">' + results.getPoi(i).title + '</span></li>';
            //添加mark
            this.addMarker(results.getPoi(i).point, src, 1, results.getPoi(i), num, i);
        }
        html += "</ul>";
        this.pageTotal = results.getNumPages() > 4 ? 4 : results.getNumPages();
        p += '<span class="page-item pre" data-index="pre">上一页</span>';
        var classPrex = '';
        for (var i = 0; i < this.pageTotal; i++) {
            classPrex = results.getPageIndex() == i ? 'active' : '';
            var num = i + 1;
            p += '<span data-index="' + i + '" class="page-item ' + classPrex + '">' + num + '</span>';
        }
        p += '<span class="page-item next" data-index="next">下一页</span>';

        $(".map-searchList").html(html);
        $(".map-searchList-pager").html(p);
        if (this.pageIndex == 0) {
            $(".map-searchList-pager .pre").hide();
        } else {
            $(".map-searchList-pager .pre").show();
        }
        if (this.pageIndex == this.pageTotal - 1) {
            $(".map-searchList-pager .next").hide();
        } else {
            $(".map-searchList-pager .next").show();
        }
        if (this.pageTotal == 1) {
            $(".map-searchList-pager").hide();
        } else {
            $(".map-searchList-pager").show();
        }
        this.setUIStatus(1);
        this.searchPoint.push(this.pointArray[0]);
        this.addMarker(this.pointArray[0], "/Content/images/muniao.png", 0);
        _this.map.setViewport(_this.searchPoint);
    }
};
MuniaoMap.prototype.localSearchForJT = function (results) {
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
                case '长途汽车站':
                    localSearchCache['jt']['2'] = result;
                    break;
                case '地铁站':
                    localSearchCache['jt']['4'] = result;
                    break;
            }
        }

    }
};
MuniaoMap.prototype.createJTHtml = function (results) {
    var _this = this,
        html = "",
        result,
        title,
        numIndex = 1,
        src,
        num;
    this.setUIStatus(1);
    this.searchPoint = [];
    for (var i in localSearchCache['jt']) {
        result = localSearchCache['jt'][i];
        title = result.keyword;
        src = this.config[result.keyword];
        //交通分类的title
        html += '<div class="map-result-TJList"><h4>' + title + '</h4><ul>';
        var resultLen = result.result ? result.result.length : result.getCurrentNumPois();
        for (var j = 0; j < resultLen; j++) {
            num = (numIndex > 9 ? numIndex : "0" + numIndex);
            var poi = result.result ? result.result[j] : result.getPoi(j);
            this.searchPoint.push(poi.point);
            if (!this.searchPoi[src]) {
                this.searchPoi[src] = [];
            }
            this.searchPoi[src].push(poi);
            html += '<li class="map-search-list" method="route" lat="' + poi.point.lat + '" lng="' + poi.point.lng + '" title="' + poi.title + '" data-type="air"><span class="result-orderNum">' + num + '</span><span class="search-list-title">' + poi.title + '</span></li>';
            //添加mark
            this.addMarker(poi.point, src, 1, poi, num, numIndex);
            numIndex++;
        }
        html += '</ul></div>';
    }
    this.searchPoint.push(this.pointArray[0]);
    this.addMarker(this.pointArray[0], "/Content/images/muniao.png", 0);
    _this.map.setViewport(_this.searchPoint);
    $("#map_resultList .map-searchList").html(html);
    $("#map_resultList .map-searchList-pager").hide();
};
MuniaoMap.prototype.initEvent = function (results) {
    var _this = this;
    $('.room-mapRight').delegate('.room-mapKeyword li', 'click', function (e) {
        $("#roommap").addClass("linear").css("width", "614px");
        $(".room-mapRight").addClass("linear").css("width", "236px");
        setTimeout(function () {
            $(".room-mapDetail").css("width", "204px");
            $(".map-searchList").css("overflowY", "auto");
        }, 500);
        var el = $(this),
            q = el.attr("data-name"),
            resultsFromCache = localSearchCache[q]['0'];
        $(".room-searchText").hide();
        el.parents(".room-mapRight").find(".room-mapDetail").show();
        //Zoom < 11 有些城市下搜索不到2公里内的周边服务
        if (_this.map.getZoom() < 11) {
            _this.map.setZoom(11);
        }

        _this.map.clearOverlays();
        _this.walkingRoute.clearResults();

        _this.setUIStatus(0);

        if (q == 'jt') {
            resultsFromCache = null;
        }

        keyword = _this.localSearchKeywordsMap[q];

        if (resultsFromCache) {
            _this.createSearchHtml(resultsFromCache);
        } else if (q == 'jt') {
            _this.local.search(['机场', '火车站', '长途汽车站']);
            _this.local.searchNearby(['地铁站'], _this.pointArray[0], 2000);
        } else {
            _this.local.searchNearby(keyword, _this.pointArray[0], 2000);
        }

        el.addClass('active').siblings().removeClass('active');
        $('#searchContent').attr('class', el.attr("data-name") + '-content' + ' room-mapDetail');
    }).delegate('.map-searchList .map-search-list', 'click', function (e) {
        var el = $(this);
        _this.endPoint = new BMap.Point(el.attr('lng'), el.attr('lat'));
        _this.startPoint = _this.pointArray[0];
        _this.endPoint.title = el.attr("title");
        $('.address-start').html(_this.startPoint.title);
        $('.address-start').attr('title', _this.startPoint.title);
        $('.address-end').html(_this.endPoint.title);
        $('.address-end').attr('title', _this.endPoint.title);
        _this.routeMethod = 'bus';
        _this.setUIStatus(0, $('.map-route-tool'));
        _this.busRoute.search(_this.startPoint, _this.endPoint);
        $('#routeContent .map-JT-way .bus').addClass('active').siblings().removeClass('active');
    }).delegate('#map_resultList .page-item', 'click', function (e) {
        var el = $(this),
            index = el.attr('data-index'),
            keyword = _this.getKey(_this.local.keyword),
            resultsFromCache = localSearchCache[keyword][index];
        if (el.hasClass('active')) {
            return
        }
        if (index == 'pre') {
            if (_this.pageIndex > 0) {
                _this.pageIndex--;
            } else {
                return;
            }
        } else if (index == 'next') {
            if (_this.pageIndex < _this.pageTotal - 1) {
                _this.pageIndex++;
            } else {
                return;
            }
        } else {
            _this.pageIndex = index;
        }

        if (resultsFromCache) {
            _this.createSearchHtml(resultsFromCache);
        } else {
            _this.local.gotoPage(parseInt(_this.pageIndex));
        }
    }).delegate("#routeContent .j_switch", 'click', function (e) {
        var el = $(this),
            tmpPoint;
        tmpPoint = _this.startPoint;
        _this.startPoint = _this.endPoint;
        _this.endPoint = tmpPoint;

        $('#routeContent .address-start').html(_this.startPoint.title);
        $('#routeContent .address-end').html(_this.endPoint.title);
        _this.setUIStatus(0, $('.map-route-tool'));
        _this.routeByMethod(_this.routeMethod);
    }).delegate('#routeContent .map-JT-way li', 'click', function (e) {
        var el = $(this);
        if (el.hasClass('active')) {
            return
        }
        el.addClass('active').siblings().removeClass('active');
        _this.routeByMethod(el.attr('q'));
    }).delegate('.map-routeClose', 'click', function (e) {
        var searchPoi = _this.searchPoi;
        $('#map_resultList').show();
        $('#routeContent').hide();
        _this.map.clearOverlays();
        for (var k in searchPoi) {
            var value = searchPoi[k];
            for (var i = 0; i < value.length; i++) {
                var list = value[i],
                 num = (i + 1 > 10 ? i + 1 : "0" + (i + 1));

                _this.addMarker(list.point, k, 1, list, num, i);

            }
        }
        _this.addMarker(_this.pointArray[0], "/Content/images/muniao.png", 0);
        _this.map.setViewport(_this.searchPoint);
    })
};
MuniaoMap.prototype.bMapInit = function () {
    /*创建地图*/
    var _this = this,
        lng = $("#roommap").attr("lng"),
        lat = $("#roommap").attr("lat"),
        point;
    this.map = new BMap.Map("roommap", { enableMapClick: false });
    this.map.disableScrollWheelZoom();
    this.map.addControl(new BMap.NavigationControl());
    this.map.addControl(new BMap.ScaleControl({ anchor: BMAP_ANCHOR_BOTTOM_LEFT }));
    point = new BMap.Point(lng, lat);
    point.title = $("#roommap").attr("name");
    point.address = $("#roommap").attr("address");
    this.addMarker(point, "/Content/images/muniao.png", 0);
    var zoom = { zoomFactor: this.pointArray.length > 2 ? 0 : -3, margins: [10, 10, 10, 10] };
    console.log(zoom);
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
                    var wr = ret.ur || ret.mr || ret.or || ret.wr || ret.xr || ret.vr || ret.zr || ret.Ar;
                    if (!wr)
                        continue;
                    for (var i = 0; i < wr.length; i++) {
                        var data = wr[i];
                        if (i == 8) break;
                        data.iconTag = iconTag;
                        datas.push(data);
                    }
                }
                for (var z = 0; z < datas.length; z++) {
                    var list = datas[z];
                    _this.addMarker(list.point, list.iconTag, 1, list, "", z + _this.num * 300);
                }
                _this.num++;
                return false;
            }
            //根据结构返回数据构建html
            _this.createSearchHtml(results);
            //缓存搜索结果
            if ($.isArray(results)) {
                // localSearchCache[getKey(local.keyword)] = results;
            } else {
                localSearchCache[_this.getKey(_this.local.keyword)][results.getPageIndex()] = results;
            }
        }
    });

    //步行路线
    this.walkingRoute = new BMap.WalkingRoute(_this.map, {
        renderOptions: { map: _this.map, autoViewport: true },
        onSearchComplete: function (results) {
            var numPlans = results.getNumPlans(),
                plan,
                route,
                html = "";

            _this.map.clearOverlays();
            _this.busRoute.clearResults();
            _this.drivingRoute.clearResults();

            if (numPlans && numPlans > 0) {
                plan = results.getPlan(0);
                route = plan.getRoute(0);

                //步行时间大于1小时，提示选择公交或者驾车
                if (plan.getDuration(false) > 3600) {
                    _this.setUIStatus(3, $('.map-route-tool'), '步行时间超过1小时，建议选择公交或驾车前往。');
                    return;
                }

                html += '<div class="map-result-route"><div class="map-route-timeTotal"><span class="schemeName">约' + plan.getDistance() + '&nbsp;&nbsp;步行' + plan.getDuration() + '</span></div><div class="map-route-step"><ul><li class="first"  title="' + _this.startPoint.title + '"><b class="address-start-icon map-step-location">起</b>' + _this.startPoint.title + '</li>';
                for (var i = 0; i < route.getNumSteps() ; i++) {
                    if (i == 0) {
                        html += '<li><i class="icon_map_foot"></i><span class="">' + route.getStep(i).getDescription(false) + '</span></li>';
                    } else {
                        html += '<li><span class="">' + route.getStep(i).getDescription(false) + '</span></li>';
                    }
                }
                html += '<li class="last" title=' + _this.endPoint.title + '><b class="address-end-icon map-step-location">终</b>' + _this.endPoint.title + '</li></ul></div></div>';
                $(".map-route-tool").html(html);
                _this.setUIStatus(2);
            } else {
                _this.setUIStatus(3, $('.map-route-tool'));
            }
        }
    });

    //驾车路线
    this.drivingRoute = new BMap.DrivingRoute(_this.map, {
        renderOptions: { map: _this.map, autoViewport: true },
        onSearchComplete: function (results) {
            var numPlans = results.getNumPlans(),
                plan,
                route,
                html = "";

            _this.map.clearOverlays();
            _this.walkingRoute.clearResults();
            _this.busRoute.clearResults();

            if (numPlans && numPlans > 0) {
                plan = results.getPlan(0);
                route = plan.getRoute(0);
                html += '<div class="map-result-route"><div class="map-route-timeTotal"><span class="schemeName">约' + plan.getDistance() + '&nbsp;&nbsp;驾车' + plan.getDuration() + '</span></div><div class="map-route-step"><ul> <li class="first" title="' + _this.startPoint.title + '"><b class="address-start-icon map-step-location">起</b>' + _this.startPoint.title + '</li>';
                for (var i = 0; i < route.getNumSteps() ; i++) {
                    if (i == 0) {
                        html += '<li><i class="icon_map_car"></i><span class="">' + route.getStep(i).getDescription(false) + '</span></li>';
                    } else {
                        html += '<li><span class="">' + route.getStep(i).getDescription(false) + '</span></li>';
                    }
                }
                html += '<li class="last" title=' + _this.endPoint.title + '><b class="address-end-icon map-step-location">终</b>' + _this.endPoint.title + '</li></ul></div></div>';
                $(".map-route-tool").html(html);
                _this.setUIStatus(2);
            } else {
                _this.setUIStatus(3, $('.map-route-tool'));
            }
        }
    });

    //公交路线
    this.busRoute = new BMap.TransitRoute(_this.map, {
        renderOptions: { map: _this.map, autoViewport: true },
        onSearchComplete: function (results) {
            var html = "",
                plan,
                numPlans = results.getNumPlans();

            if (numPlans && numPlans > 0) {
                plan = results.getPlan(0);
                html += '<div class="map-result-route"><div class="map-route-timeTotal"><span class="schemeName">约' + plan.getDistance() + '&nbsp;&nbsp;' + plan.getDuration() + '</span></div><div class="map-route-step"><ul class="bus_start"><li class="first" title="' + _this.startPoint.title + '"><b class="address-start-icon map-step-location">起</b>' + _this.startPoint.title + '</li></ul>' + results.getPlan(0).getDescription() + '<ul class="bus_end"><li class="last" title=' + _this.endPoint.title + '><b class="address-end-icon map-step-location">终</b>' + _this.endPoint.title + '</li></ul></div>';
                $(".map-route-tool").html(html);
                _this.setUIStatus(2);
            } else {
                _this.setUIStatus(3, $('.map-route-tool'), '抱歉，暂无公交路线，请查看驾车或步行');
            }
        }
    });
    //事件绑定
    this.initEvent();
    //初始化默认显示交通
    this.local.search(['机场', '火车站', '长途汽车站']);
    this.local.searchNearby(['地铁站', "餐饮", "景点", "购物", "娱乐"], point, 2000);
};
function addMouseoverHandler(i, marker, obj) {
    marker.addEventListener("mouseover", function () {
        var leftoffset = 0;
        var rightoffset = 0;
        var topoffset = 0;
        if (($('#label_' + i).parent('.BMapLabel').offset().left - $('#roommap').offset().left) < 49) {
            leftoffset = 120 - ($('#label_' + i).parent('.BMapLabel').offset().left - $('#roommap').offset().left);
        }
        if (($('#label_' + i).parent('.BMapLabel').offset().left - $('#roommap').offset().left + 45) > $('#roommap').width()) {
            rightoffset = $('#label_' + i).parent('.BMapLabel').offset().left - $('#roommap').offset().left - $('#roommap').width() + 90;
        }
        if (($('#label_' + i).parent('.BMapLabel').offset().top - $('#roommap').offset().top) < 49) {
            topoffset = 60 - ($('#label_' + i).parent('.BMapLabel').offset().top - $('#roommap').offset().top);
        }

        if (leftoffset != 0) {
            obj.map.panBy(leftoffset, topoffset);
        }
        if (rightoffset != 0) {
            obj.map.panBy(-rightoffset, topoffset);
        }
        if (leftoffset == 0 && rightoffset == 0) {
            obj.map.panBy(0, topoffset);
        }
        $("#label_" + i).show();
        this.setTop(true, 2700000);
    });
    marker.addEventListener("mouseout", function () {
        $("#label_" + i).hide();
        this.setTop(false);
    });
}