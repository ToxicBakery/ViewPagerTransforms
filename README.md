[![CircleCI](https://circleci.com/gh/ToxicBakery/ViewPagerTransforms.svg?style=svg)](https://circleci.com/gh/ToxicBakery/ViewPagerTransforms)
[![License](https://img.shields.io/badge/license-Apache%202.0%20License-blue.svg)](https://github.com/ToxicBakery/ViewPagerTransforms/blob/master/LICENSE)
[![Maven Central](https://img.shields.io/maven-metadata/v/https/oss.sonatype.org/content/repositories/releases/com/ToxicBakery/viewpager/transforms/view-pager-transforms/maven-metadata.xml.svg)](https://oss.sonatype.org/content/repositories/releases/com/ToxicBakery/viewpager/transforms/view-pager-transforms)

ViewPagerTransforms
===================

Library containing common animations needed for transforming ViewPager scrolling on Android v13+. This library is a rewrite of the [JazzyViewPager](https://github.com/jfeinstein10/JazzyViewPager) library and owes credit of the animation concepts directly to its source. The purpose of this rewrite is to provide an easier to use and extend implementation of ViewPager animations.

![Demo](http://i.imgur.com/rvhE2ns.gif)

# Getting Started (Gradle / Android Studio)

Add gradle dependency to your application.
```gradle
implementation 'com.ToxicBakery.viewpager.transforms:view-pager-transforms:2.0.24'
```

After configuration, instantiate the transformer animation you wish to use and set it as the [page transformer](https://developer.android.com/reference/android/support/v4/view/ViewPager.html#setpagetransformer_1).

```java
// Reference (or instantiate) a ViewPager instance and apply a transformer
pager = (ViewPager) findViewById(R.id.container);
pager.setAdapter(mAdapter);
pager.setPageTransformer(true, new RotateUpTransformer());
```

# Creating Custom Transforms

All ViewPagerTransform implementations extend [ABaseTransformer](https://github.com/ToxicBakery/ViewPagerTransforms/blob/master/library/src/main/java/com/ToxicBakery/viewpager/transforms/ABaseTransformer.java) providing useful hooks improving readability of animations and basic functionality important when switching between animations. [ABaseTransformer](https://github.com/ToxicBakery/ViewPagerTransforms/blob/master/library/src/main/java/com/ToxicBakery/viewpager/transforms/ABaseTransformer.java) provides three lifecycle hooks and two flags for default handling of hiding offscreen fragments and mimicking the default paging functionality of the ViewPager.

* [onPreTransform(View view, float position)](https://github.com/ToxicBakery/ViewPagerTransforms/blob/master/library/src/main/java/com/ToxicBakery/viewpager/transforms/ABaseTransformer.java#L85)
  * Default implementation resets the animation state of the fragment to defaults that will place it on the screen if its position permits.
* [onTransform(View view, float position)](https://github.com/ToxicBakery/ViewPagerTransforms/blob/master/library/src/main/java/com/ToxicBakery/viewpager/transforms/ABaseTransformer.java#L33)
  * Animations should perform all or most of their work inside this callback.
* [onPostTransform(View view, float position)](https://github.com/ToxicBakery/ViewPagerTransforms/blob/master/library/src/main/java/com/ToxicBakery/viewpager/transforms/ABaseTransformer.java#L116)
  * Default implementation does nothing. This provides a logical location for any additional work to be done that is not directly related to the animation.

## Building
This project is built with Gradle using the Gradle Wrapper script.

```bash
./gradlew build
```

## Creating Local Versions
You can modify this project and create local packages with via the maven publish plugin used in the build scripts.

```bash
./gradlew publishToMavenLocal
```
