$.jqx.theme = 'bootstrap';

$(function() {
    'use strict';

    $(document).find('input[type="text"],input[type="email"],input[type="number"]').each(function(idx, elem) {
        $(elem).jqxInput({height: '25'});
    });

    $(document).find('textarea').each(function(idx, elem) {
        $(elem).jqxInput({width: '250', height: '50'});
    });

    $(document).find('button').each(function(idx, elem) {
        $(elem).jqxButton({width: '80'});
    });
});