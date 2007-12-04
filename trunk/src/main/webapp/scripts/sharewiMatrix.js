var cellFuncs = [
        function(data) {return data.srcId; },
        function(data) {return data.dstId; },
        function(data) {return data.dist; },
        function(data) {return data.time; }];

var paths;
var index;
var directions;

// Returns a GDirections query string from
// a 'path' object: "lat,long to lat,long"
function makeQueryStr(path) {
    return paths[index].srcGeocode.latitude + "," + paths[index].srcGeocode.longitude + " to " +
           paths[index].dstGeocode.latitude + "," + paths[index].dstGeocode.longitude;
};


// Handles callback from loadedAll()
// Redirects upon successful DWR paths update
function updatedPaths(data) {
    $("form:btnDone").click();
    $('progressSpinner').hide();
    return false;
};

// Called by loaded(),
// Handles completion of loading for
// all paths in the array
// Updates all 'paths' via DWR
function loadedAll() {

    //Send the updated paths back to the server for processing
    pathDWRManager.updatePathsFromWrappers(paths, updatedPaths);
    return false;
};

// Handles Event.load, sets time
// and distance properties of paths
function loaded() {
    //To handle the "symmetric" approach
    var distance = directions.getDistance().meters;
    var duration = directions.getDuration().seconds;

    paths[index].dist = distance;
    paths[index].time = duration;

    paths[index+1].dist = distance;
    paths[index+1].time = duration;


    //Print the updated path to the table
    dwr.util.addRows("tbody", [paths[index],paths[index+1]], cellFuncs);

    //  +2  for symmetric approach
    index = index + 2;
    if (paths[index] != null) directions.load(makeQueryStr(paths[index]));
    else loadedAll();
};

// Handles paths data callback from initialize()
// and initiates the directions loading
function process(data) {
    if (data != null && typeof data == 'object') {

        //Make data global
        paths = data;

        //Initialize index
        index = 0;

        $('progressSpinner').show();

        //Make a new directions objects and fetch
        directions.load(makeQueryStr(paths[index]));
    }

    else alert(dwr.util.toDescriptiveString(data, 1));
};

// Initializes variables and
// sends for paths through DWR
function initialize(locationId) {
    locationDWRManager.preparePathWrappers(eval(locationId), process);
    directions = new GDirections();
    GEvent.addListener(directions, "load", loaded );

    // Visual stuff
    $('progressSpinner').hide();
    dwr.util.removeAllRows("matrixTable:tbody_element");
    return false;
};