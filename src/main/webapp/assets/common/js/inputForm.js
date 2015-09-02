var inputForm = function() {

    return {
        serialize: function(formId) {
            var $form = $('#' + formId);
            if ($form.length === 0) {
                console.log('Form id ' + formId + ' not found');
                return '';
            }
            var disabled = $form.find('input:disabled,select:disabled,textarea:disabled').removeAttr('disabled');
            var serialized = $form.serialize();
            disabled.attr('disabled', 'disabled');

            $form.find('div[role="checkbox"],div[role="radio"]').each(function(idx, elem) {
                if ($(elem).attr('aria-checked') === 'true') {
                    var name = $(elem).data('name'),
                        value = $(elem).data('value');
                    if ($.trim(name).length > 0) {
                        serialized += '&' + name + '=' + $.trim(value);
                    }
                }
            });

            $form.find('div[role="textbox"]').each(function(idx, elem) {
                var name = $(elem).data('name');
                if ($.trim(name).length > 0) {
                    serialized += '&' + name + '=' + $.trim($(elem).attr('aria-valuetext'));
                }
            });

            return serialized;
        }
    };
}();