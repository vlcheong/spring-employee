/*
 * depends on jQuery
 */
var OK = 'OK',
    ERROR = 'ERROR',
    ajax = function () {
        'use strict';
        var defaultErrorHandler =
            function (jqXHR, textStatus, errorThrown) {
                console.error('textStatus: ' + textStatus +
                              ', errorThrown: ' + errorThrown);
                var msg = '';
                if (jqXHR.status === 0) {
                    msg += 'Not connected. Please verify your network connection';
                } else if (jqXHR.status === 401) {
                    msg += 'Unauthorized access';
                } else if (jqXHR.status === 403) {
                    msg += 'Forbidden';
                } else if (jqXHR.status === 404) {
                    msg += 'The requested page not found';
                } else if (jqXHR.status === 405) {
                    msg += 'Method not allowed';
                } else if (jqXHR.status === 500) {
                    msg += 'Internal server error';
                } else if (errorThrown === 'parsererror') {
                    msg += 'Requested JSON parse failed';
                } else if (errorThrown === 'timeout') {
                    msg += 'Time out error';
                } else if (errorThrown === 'abort') {
                    msg += 'Request aborted by the server';
                } else {
                    msg += 'Application error\n' + jqXHR.responseText;
                }
                console.error(msg);
                window.alert(msg);
        };

        var send = function (method, request) {
            $.ajax({
                url:request.url,
                type:method,
                async:(request.hasOwnProperty('async') ? request.async : true),
                cache:false,
                timeout:900000,
                data:(request.hasOwnProperty('data') ? request.data : {}),
                dataType:(request.hasOwnProperty('dataType') ? request.dataType : 'json')
            })
            .done(function (data, textStatus, jqXHR) {
                var timeout = jqXHR.getResponseHeader('Timeout');
                // console.log('timeout:' + timeout + ':');
                if (timeout === 'true') {
                    // console.log('data:' + data + ' typeof:' + (typeof data));
                    var json = data;
                    if (typeof data === 'string') {
                        json = JSON.parse(data);
                    }
                    if (json.statusCode === ERROR) {
                        window.alert(json.messages.join('\n'));
                        window.location.href = json.url;
                        return;
                    }
                }
                if (request.hasOwnProperty('callback')) {
                    request.callback(data, textStatus, jqXHR);
                } else {
                    console.warn('No callback implemented!');
                }
            })
            .fail(function (jqXHR, textStatus, errorThrown) {
                if (request.hasOwnProperty('error')) {
                    request.error(jqXHR, textStatus, errorThrown);
                } else {
                    defaultErrorHandler(jqXHR, textStatus, errorThrown);
                }
            });
        };
        return {
            post: function (request) {
                send('POST', request);
            },
            get: function (request) {
                send('GET', request);
            }
        }
    }();