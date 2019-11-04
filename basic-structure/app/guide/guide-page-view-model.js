const observableModule = require("tns-core-modules/data/observable");

function dataLoader() {
    if(global.guideObject == undefined) {
        global.guideObject = require('./manual.json') //this (necessarily) is synchronous 
        global.guideChapters = Object.keys(global.guideObject)
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
    const viewModel = observableModule.fromObject(dataLoader());
    return viewModel;
}
module.exports = GuideViewModel;
