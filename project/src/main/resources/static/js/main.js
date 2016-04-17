/** Dynamically loads the javascript file corresponding to the page requested */
var sourceFile = document.getElementById('content').getAttribute('data-view') + "_view.js";

head.load([
    "js/jquery-2.2.2.min.js",
    "js/bootstrap.min.js",
    "js/handlebars-v4.0.5.min.js",
    "js/app.js",
    "js/"+sourceFile
]);