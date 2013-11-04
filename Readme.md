# uima-zipper

UIMA annotation engine for multi-key mapping.

## Usage

It "zip"s the multiple feature values using mapping table, then set the mapped values to the target feature. For each source annotation, if the source annotation covers a annotation qualified as a target (i.e. if we can find the target on `Annotation.subiterator(sourceAnnotation)`), then the value of the targe annotation is updated. Otherwise a new annotation with the mapped value is created and added to index.

```
          (configuration parameters)
        type and features to be mapped
                      |
                      v
 Source -------------------------------> Target
 - hoge               ^                  - foo
 - fuga               |
                      |
                mapping table
             (external resource)
```

## Configuration Parameters

### SourceType (String)

Name of the type whose feature values are used as mapping keys (e.g. `net.hitsujiwool.uima.zipper.Source`).

### SourceFeatures (String[])

List of the name of the feature whose values are used as mapping keys (e.g. `hoge, fuga`).

### TargetType (String)

Name of the type which is used as a maping target (e.g. `net.hitsujiwool.uima.zipper.Target`).

### TargetFeature (String)

Name of the feature in which the mapped value is inserted (e.g. `foo`).

## External Resource

For an example, let we think about some mapping functions like `("baaaa", "bleat") => "baaaableat"` and `("baaaaaa", "bleeeat") => "baaaaaableeeat"`.

First you need to create a mapping table written in XML.

```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<mapping>  
  <map>
    <sources>
      <source feature="hoge" value="baaaa" />
      <source feature="fuga" value="bleat" />
    </sources>
    <target value="baaaableat" />
  </map>
  <map>
    <sources>
      <source feature="hoge" value="baaaaaa" />
      <source feature="fuga" value="bleeeat" />
    </sources>
    <target value="baaaaaableeeat" />
  </map>
</mapping>
```

Then, in your annotation engine desciptor XML, specify `net.hitsujiwool.uima.resource.MultiKeyMapResource` as an external resource implementation, and bind it to resource whose interface is `net.hitsujiwool.uima.resource.MultiKeyMap`. See [UIMA official documents](http://uima.apache.org/documentation.html) for more general information about resource settings.

## Note

* Values which do not match any mapping rules are ignored.

## License

The MIT License (MIT)

Copyright (c) 2013 Ryo Murayama &lt;utatanenohibi@gmail.com&gt;

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
