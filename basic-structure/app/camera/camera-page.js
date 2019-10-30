const cameraViewModel = require("./camera-view-model");

function onNavigatingTo(args) {
    const component = args.object;
    component.bindingContext = new cameraViewModel();
}

exports.onNavigatingTo = onNavigatingTo;
