const guidePageViewModel = require("./guide-page-view-model");
var observableModule = require("tns-core-modules/data/observable")
var ObservableArray = require("tns-core-modules/data/observable-array").ObservableArray;
var tileList = new guidePageViewModel();// {"_observers"{}, _array{<our tile objects>}}

function onNavigatingTo(args) {
    const component = args.object;//the page
    component.bindingContext = tileList;
}

function onItemTap(args) {
    const view = args.view;
    const page = view.page;
    const tappedItem = view.bindingContext;

    page.frame.navigate({
        moduleName: "guide/guide-detail/guide-detail-page",
        context: tappedItem,
        animated: true,
        transition: {
            name: "slide",
            duration: 200,
            curve: "ease"
        }
    });
}

exports.onItemTap = onItemTap;
exports.onNavigatingTo = onNavigatingTo;
