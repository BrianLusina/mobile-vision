const functions = require('firebase-functions');
const vision = require("@google-cloud/vision")();
const admin = require("firebase-admin");

admin.initializeApp(functions.Config().firebase);

// todo:
// remove console.log statments and replace with a Logging Service. Rollbar
// Sentry, etc
exports.callVision = functions.storage.object().onChange(event => {
	const object = event.data;
	const fileBucket = object.bucket;
	const filePath = object.name;
	const gcsPath = `gs://${fileBucket}/${filePath}`;

	// Prepare the request object
    	let request = {
		image: {source: {imageUri: gcsPath}},
      		features: [
			{ 
				type: Vision.v1.types.Feature.Type.WEB_DETECTION
			},
			{ 
				type: Vision.v1.types.Feature.Type.SAFE_SEARCH_DETECTION 
			}
		],
	};

	return vision.annotateImage(request).then(response => {
		let webDetection = response[0].webDetection;
		let safeSearch = response[0].safeSearchAnnotation;
		return {web: webDetection, safe: safeSearch};
	}).then((visionResponse) => {
		let db = admin.firestore();
		let imageRef = db.collection("images).doc(filePath.slice(7));
		return imageRef.set(visionResponse);
	}).catch(error => {
		console.log('Vision API all failed with ', error);
	})
});
