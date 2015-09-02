var content = function() {
    'use strict';
    return {
        toggle: function() {
            if ($('div#primary-content').is(':visible')) {
                $('div#secondary-content').fadeIn();
                $('div#primary-content').fadeOut('fast');
            } else {
                $('div#primary-content').fadeIn();
                $('div#secondary-content').fadeOut('fast');
            }
        },
        primary: function(html) {
            $('div#primary-content').html(html);
            return this;
        },
        secondary: function(html) {
            $('div#secondary-content').html(html);
            return this;
        },
        restore: function(target, entity) {
            var $form = $(target);
            if ($form.length === 0) {
                console.error('Selector "' + target + '" not found');
                return;
            }
            var elems = $form[0].elements,
                len = elems.length;
            for (var i = 0; i < len; i++) {
                var value = entity[elems[i].name];
                if (value) {
                    if (elems[i].type === 'checkbox') {
                        if (value.toUpperCase() === 'CHECKED' ||
                            value.toUpperCase() === 'Y' ||
                            value.toUpperCase() === 'YES') {
                            elems[i].checked = true;
                        } else {
                            elems[i].checked = false;
                        }
                    } else if (elems[i].type === 'radio') {
                        if (elems[i].value === value) {
                            elems[i].checked = true;
                        } else {
                            elems[i].checked = false;
                        }
                    } else {
                        elems[i].value = value;
                    }
                }
            }
            return this;
        },
        isError: function(html) {
            var $error = $('div[id="$error"]');
            if ($error.length === 0) {
                return false;
            } else {
                var errs = '';
                $error.find('#data ul li').each(function(idx, elem) {
                    if (idx > 0) errs += '\n';
                    errs += $(elem).text();
                });
                alert(errs);
                return true;
            }
        },
        trigger: function(target) {
            var $target = $(target);
            if ($target.length > 0) {
                $target.trigger('click');
            } else {
                console.error('Selector "' + target + '" not found');
            }
        }
    };
}();