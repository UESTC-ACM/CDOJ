/**
 * Created with IntelliJ IDEA.
 * User: mzry1992
 * Date: 13-7-8
 * Time: 下午4:14
 * To change this template use File | Settings | File Templates.
 */

function getRatingColor(rating) {
    var ratingColor;
    if (rating < 900)   ratingColor = "gray";
    else if (rating < 1200) ratingColor = "green";
    else if (rating < 1500) ratingColor = "blue";
    else if (rating < 2200) ratingColor = "yellow";
    else    ratingColor = "red";
    return ratingColor;
}

function getRating(rating, ratingVary) {
    var color = getRatingColor(rating);
    rating = Math.floor(rating);
    var html = $('<td style="text-align: right;"></td>');
    var ratingSpan = $('<span class="rating-' + color + ' label-rating">' + rating + '</span>');
    var varySpan = $('<span class="label label-diff"></span>');
    var isFirst = (ratingVary == null);
    if (isFirst) {
        varySpan.addClass('label-info');
        varySpan.append('INIT');
    } else {
        ratingVary = Math.floor(ratingVary);
        if (ratingVary >= 0) {
            varySpan.addClass('label-success');
            varySpan.append('+' + ratingVary);
        } else {
            varySpan.addClass('label-important');
            varySpan.append(ratingVary);
        }
    }
    html.append(ratingSpan);
    html.append(varySpan);
    return html;
}

function getVolatility(volatility, volatilityVary) {
    volatility = Math.floor(volatility);

    if (volatilityVary != null) {
        volatilityVary = Math.floor(volatilityVary);
        return '<td>' + volatility +
            '(' + (volatilityVary >= 0 ? '+' : '') + volatilityVary +
            ')</td>';
    } else {
        return '<td>' + volatility +
            '(INIT)</td>';
    }
}

