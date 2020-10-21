### SAMCode Programming Language
#### Variables
1. To create a `text` variable, type `text`, then the name of the variable with a colon, then the value: `text hello: Hello, World!` Texts do accept escape sequences like `\t` and `\n`.
2. To create a `num` variable, type `num`, then the name of the variable with a colon, then the value: `num tenDigitsOfPi: 3.141592653` Nums can be decimal or integer numbers, but will be treated as decimal numbers.
3. Changing the value works the same as creating a variable, just leave out the data type (num or text). Example: `hello: Hello, Universe!`.
4. Nums will evaluate expressions like `num myNumber: 123 - 8 * 8`, however, they are calculated from left to right, *not* using the order of operations. The output for this expression would be `920`.
5. When referencing a variable inside a numerical expression parantheses are not required. Example: `num a: 5 + x + a`
#### Print to Console
- Use the `write` keyword followed by a text to print to the console. A variable can be referenced in a text by using parantheses around it.
#### Take in input
- Use the `input` keyword followed by `as` and the data type (`num` or `text`). Add a colon and then the variable name to be stored in. Example: `input as num: age` or `input as text: name`
#### Conditionals
- `if` statements
	- Use the `if` keyword followed by a value, then an operator to compare to another value, then another value and a semicolon
	- Take note that, unlike other programming languages, you only need a single equals sign
	- You may use `AND` or `OR` between conditionals
	- All statements following the `if` statement before an `endif` statement will run if the conditional is true.
#### Comments
- Use `*` followed by a space before a line to indicate it is a comment. A comment will skip the line it is placed on.
#### Example program (BMI Calculator)
```
* Ask for weight in pounds
	write Enter weight in pounds:
		input as num: weightInPounds
* Ask for height in inches
	write \nEnter height in inches:
		input as num: heightInInches
* Convert pounds to kilograms
	num weightInKilograms: weightInPounds / 2.205
* Convert inches to meters
	num heightInMeters: heightInInches / 39.37
* Calculate the height squared
	num heightSquared: heightInMeters * heightInMeters
* Calculate the BMI
	num BMI: weightInKilograms / heightSquared
		write \nBMI: (BMI)
* Initialize category to underweight
	text category: underweight
* Check for other categories
	if BMI >= 18.5 AND BMI < 24.9:
		category: normal
		endif
	if BMI >= 25.0 AND BMI < 30:
		category: overweight
		endif
	if BMI >= 30:
		category: obtuse
		endif
* Write the category
	write \n\nCategory: (category)\n\n
```
##### Sample Output
```
Enter weight in pounds: 200

Enter height in inches: 60

BMI: 39.05257999496094 

Category: obtuse
```
