const observableModule = require("tns-core-modules/data/observable");
function dataLoader() {
    const data = "Animals\n" +
        "Apps\n" +
        "Camouflage\n" +
        "Cold\n" +
        "DangerousArthropods\n" +
        "Desert\n" +
        "DirectionFinding\n" +
        "Fire\n" +
        "FishAndMollusks\n" +
        "Food\n" +
        "HostileAreas\n" +
        "Introduction\n" +
        "Kits\n" +
        "ManMadeHazards\n" +
        "Medicine\n" +
        "MultiTool\n" +
        "People\n" +
        "Planning\n" +
        "Plants\n" +
        "PoisonousPlants\n" +
        "Power\n" +
        "Psychology\n" +
        "RopesAndKnots\n" +
        "Sea\n" +
        "Self-defense\n" +
        "Shelter\n" +
        "Signaling\n" +
        "Tools\n" +
        "Tropical\n" +
        "Water\n" +
        "WaterCrossing";
    const dataArr = data.split("\n");
    const template = {};
    template.items = [];
    dataArr.map((subject) => {
        const tempObj = {};
        tempObj.name = subject;
        tempObj.description = `Description for ${subject}`;
        console.log(tempObj);
        template.items.push(tempObj);
    });
    return template;
}
function GuideViewModel() {
    const viewModel = observableModule.fromObject(dataLoader());
    return viewModel;
}
module.exports = GuideViewModel;
