var DATE_FORMAT = {
    formatString: 'yyyy-MM-dd',
    height: '25px',
    showFooter: true,
    allowNullDate: true,
    min: new Date(1945, 11, 31),
    todayString: 'Today',
    clearString: 'Clear',
};

jQuery.fn.extend({
    dateTimeInput: function(options) {
        jQuery.extend(DATE_FORMAT, options);
        $(this).jqxDateTimeInput(DATE_FORMAT);
        $(this).on('change', function (event) {
            $(this).attr('aria-valuetext', $(this).val());
        });
    }
});