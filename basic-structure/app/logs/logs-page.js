const logsViewModel = require("./logs-view-model");

function onNavigatingTo(args) {
    const component = args.object;
    component.bindingContext = new logsViewModel();
}

exports.onNavigatingTo = onNavigatingTo;
