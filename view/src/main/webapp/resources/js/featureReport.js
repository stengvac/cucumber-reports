function replaceData(target, placeholder, data) {

    if ($(target).hasClass("data-embedded")) {
        $(target).addClass('placeholder').removeClass('bold').removeClass("data-embedded").html(placeholder);
    } else {
        $(target).addClass("data-embedded").addClass('bold').removeClass("placeholder").html(data);
    }

    return false;
}

//Register change function - will show/hide passed scenarios
$(document).ready(function () {
    $('#showFailedOnly').change(function () {
        $('#scenarioNames').find('.text-success').toggleClass('hide');
        $('#scenarioReports').find('.bs-callout-success').toggleClass('hide');
    });
});

//By default hide background section
$(document).ready(function () {
    $('.background').toggleClass('hide');

    $('#showBackgrounds').change(function () {
        $('.background').toggleClass('hide');
    });
});

//By default hooks should be hidden
$(document).ready(function () {
    $('.hooks').toggleClass('hide');

    $('#showHooks').change(function () {
        $('.hooks').toggleClass('hide');
    });
});

//instead of placeholders show data in step definitions
$(document).ready(function () {
    $('.placeholder').each(function () {
        $(this).click();
    });

});