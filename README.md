# Gabber
Gabber is a fictional language toolbox in Java. With it, you can generate random languages, mix them together or with
predefined imitations of natural languages, generate text in them, reversibly translate English text to a fictional
language, and so on. It is drawn from code in [SquidLib](https://github.com/SquidPony/SquidLib), but only does
language-related tasks, and does not take on any of SquidLib's other roles. It is meant to work cross-platform,
including to Android and GWT as well as desktop applications. It has one dependency (that also works cross-platform),
RegExodus, which provides expanded regular expression support.

The code is currently in the process of being brought in from SquidLib and altered to need fewer support classes.
In the `gabber` package, `Language` is the main piece of code, and it should be complete for now. The code for
translating reversibly is coming soon. When the main body of code is done, the plan is to publish Gabber to Maven
Central, using the groupid `com.github.tommyettinger` and the artifactid `gabber`.

# Other
Gabber is the Dutch equivalent of the English word "jabber," meaning to speak meaninglessly, but is also a genre of
fast, chaotic electronic music that I happened to be listening to at the time I created the repo.
