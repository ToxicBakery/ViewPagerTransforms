package com.ToxicBakery.viewpager.transforms;

/**
 * Created by TacB0sS on 07-Aug 2015.
 *
 * @author TacB0sS
 */
public enum TransformerType {
	Default("Default", DefaultTransformer.class),
	Accordion("Accordion", AccordionTransformer.class),
	BackgroundToForeground(BackgroundToForegroundTransformer.class),
	CubeIn(CubeInTransformer.class),
	CubeOut(CubeOutTransformer.class),
	DepthPage(DepthPageTransformer.class),
	FlipHorizontal(FlipHorizontalTransformer.class),
	FlipVertical(FlipVerticalTransformer.class),
	ForegroundToBackground(ForegroundToBackgroundTransformer.class),
	RotateDown(RotateDownTransformer.class),
	RotateUp(RotateUpTransformer.class),
	ScaleInOut(ScaleInOutTransformer.class),
	Stack(StackTransformer.class),
	Tablet(TabletTransformer.class),
	ZoomIn(ZoomInTransformer.class),
	ZoomOut(ZoomOutTranformer.class),
	ZoomOutSlide(ZoomOutSlideTransformer.class),;

	private final ABaseTransformer transformer;

	private final String niceName;

	TransformerType(Class<? extends ABaseTransformer> transformerType) {
		this(transformerType.getSimpleName(), transformerType);
	}

	TransformerType(String niceName, Class<? extends ABaseTransformer> transformerType) {
		this.niceName = niceName;
		try {
			transformer = transformerType.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("MUST HAVE A DEFAULT PUBLIC CONSTRUCTOR FOR TYPE: " + transformerType);
		}
	}

	public final ABaseTransformer getTransformer() {
		return transformer;
	}

	@Override
	public String toString() {
		return niceName;
	}
}

