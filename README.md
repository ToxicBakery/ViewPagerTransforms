ViewPagerTransforms
===================

Library containing common animations needed for transforming ViewPager scrolling on Android v13+. This library is a rewrite of the [JazzyViewPager](https://github.com/jfeinstein10/JazzyViewPager) library and owes credit of the animation concepts directly to its source. The purpose of this rewrite is to provide an easier to use and extend implementation of ViewPager animations.

![Demo](http://i.imgur.com/rvhE2ns.gif)

#Getting Started

Simply import this project into your IDE and reference the Library as an Android Library Project from your project. After configuration, instantiate the transformer animation you wish to use and set it as the [page transformer](http://developer.android.com/reference/android/support/v4/view/ViewPager.html#setPageTransformer(boolean, android.support.v4.view.ViewPager.PageTransformer)).

```java
// Reference (or instantiate) a ViewPager instance and apply a transformer
pager = (ViewPager) findViewById(R.id.container);
pager.setAdapter(mAdapter);
pager.setPageTransformer(true, new RotateUpTransformer());
```

#Creating Custom Transforms

All ViewPagerTransform implementations extend [ABaseTransformer](https://github.com/ToxicBakery/ViewPagerTransforms/blob/master/ViewPagerTransformsLibrary/src/com/ToxicBakery/viewpager/transforms/ABaseTransformer.java) providing useful hooks improving readability of animations and basic functionality important when switching between animations. [ABaseTransformer](https://github.com/ToxicBakery/ViewPagerTransforms/blob/master/ViewPagerTransformsLibrary/src/com/ToxicBakery/viewpager/transforms/ABaseTransformer.java) provides three lifecycle hooks and two flags for default handling of hiding offscreen fragments and mimicking the default paging functionality of the ViewPager.

* [preTransform(View view, float position)](https://github.com/ToxicBakery/ViewPagerTransforms/blob/master/ViewPagerTransformsLibrary/src/com/ToxicBakery/viewpager/transforms/ABaseTransformer.java#L42)
 * Default implementation resets the animation state of the fragment to defaults that will place it on the screen if its position permits.
* [onTransform(View view, float position)](https://github.com/ToxicBakery/ViewPagerTransforms/blob/master/ViewPagerTransformsLibrary/src/com/ToxicBakery/viewpager/transforms/ABaseTransformer.java#L14)
 * Animations should perform all or most of their work inside this callback.
* [postTransform(View view, float position)](https://github.com/ToxicBakery/ViewPagerTransforms/blob/master/ViewPagerTransformsLibrary/src/com/ToxicBakery/viewpager/transforms/ABaseTransformer.java#L75)
 * Default implementation does nothing. This provides a logical location for any additional work to be done that is not directly related to the animation.
