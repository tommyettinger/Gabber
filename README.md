# Gabber
Gabber is a fictional language toolbox in Java. With it, you can generate random languages, mix them together or with
predefined imitations of natural languages, generate text in them, reversibly translate English text to a fictional
language, and so on. It is drawn from code in [SquidLib](https://github.com/SquidPony/SquidLib), but only does
language-related tasks, and does not take on any of SquidLib's other roles. It is meant to work cross-platform,
including to Android and GWT as well as desktop applications. It has one dependency (that also works cross-platform),
RegExodus, which provides expanded regular expression support.

The code is currently complete enough to be used for the stated purposes of the library. As of version 0.2, it also
supports generating randomized choices of synonyms using the `Thesaurus` class, which also has some handy features for
producing strings that contain random words in some language style, or (very randomized) nation names that use the
random languages produced by `Language`. Documentation is present in the code, but could be better outside of it. Gabber
version 0.2 is published to Maven Central, using the groupid `com.github.tommyettinger` and the artifactid `gabber`.
[More instructions available here](http://search.maven.org/#artifactdetails%7Ccom.github.tommyettinger%7Cgabber%7C0.2%7Cjar).

GWT, or Google Web Toolkit, probably isn't that commonly used, but this is compatible with it. To use Gabber with GWT,
you need the GWT `inherits` tags:
```
     <inherits name='regexodus' />
     <inherits name='gabber' />
```

# Other
Gabber is the Dutch equivalent of the English word "jabber," meaning to speak meaninglessly, but is also a genre of
fast, chaotic electronic music that I happened to be listening to at the time I created the repo.
