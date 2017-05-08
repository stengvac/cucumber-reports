/**
 * Shows/hides all passed features.
 */
$(function () {
    $(".hidePassedFeatures").each(function () {
        var dataTableClass = $(this).attr("data-table");

        $(this).change(function () {
            $("." + dataTableClass).find("td ul li:has(a.text-success)").slideToggle();
        });
    });
});
