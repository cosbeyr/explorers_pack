const ObservableArray = require("tns-core-modules/data/observable-array").ObservableArray;
function dataLoader() {
    if (global.guideObject === undefined) {
        global.guideObject = require("../App_Resources/guide/guide.json"); //this (necessarily) is synchronous
        global.guideChapters = Object.keys(global.guideObject);
    }

    const template = {};
    template.items = [];
    global.guideChapters.forEach((chapter) => {
        const tempObj = {};
        tempObj.name = chapter;
        tempObj.description = global.guideObject[chapter].main;
        template.items.push(tempObj);
    });

    return template;
}
function GuideViewModel() {
    const viewModel = new ObservableArray(dataLoader().items);

    return viewModel;
}
module.exports = GuideViewModel;
