# AngularJS DateTimePicker Directive

## Demo
http://jackrabbitsgroup.github.io/angular-datetimepicker/

## Dependencies
- `momentjs`
- Pikaday (included in this package - it's an edited version of the Pikaday time picker fork ( https://github.com/owenmead/Pikaday ), just make sure to include it)

## Install
1. download the files
	1. Bower
		1. add `"angular-datetimepicker": "latest"` to your `bower.json` file then run `bower install` OR run `bower install angular-datetimepicker`
2. include the files in your app (make sure momentjs is included BEFORE pikaday.js)
	1. pikaday-edit.js
	2. pikaday.css
	3. datetimepicker.min.js
	4. datetimepicker.less OR datetimepicker.min.css OR datetimepicker.css
3. include the module in angular (i.e. in `app.js`) - `jackrabbitsgroup.angular-datetimepicker`

See the `gh-pages` branch, files `bower.json` and `index.html` for a full example.

## Documentation
See the `datetimepicker.js` file top comments for usage examples and documentation
https://github.com/jackrabbitsgroup/angular-datetimepicker/blob/master/datetimepicker.js