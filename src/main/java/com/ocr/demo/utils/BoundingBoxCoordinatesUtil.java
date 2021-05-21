package com.ocr.demo.utils;

import java.util.ArrayList;
import java.util.List;

import com.ocr.demo.domain.Area;
import com.ocr.demo.domain.BoxInfo;
import com.ocr.demo.domain.CoordinatesType;
import com.ocr.demo.domain.Line;
import com.ocr.demo.domain.Paragraph;
import com.ocr.demo.domain.Word;

public class BoundingBoxCoordinatesUtil {

	public static List<BoxInfo> getBoundingBoxCoordinatesForType(List<Area> areas, CoordinatesType type) {
		List<BoxInfo> results = new ArrayList<>();
		for (Area a : areas)
		{
			if (CoordinatesType.AREA == type) {
				results.add(new BoxInfo(a.getId(),
					a.getBoundingBox().getBoundingBoxCoordinates()));
			} else {
				for (Paragraph p : a.getParagraphs())
				{
					if (CoordinatesType.PARAGRAPH == type) {
						results.add(new BoxInfo(p.getId(),
							p.getBoundingBox().getBoundingBoxCoordinates()));
					} else {
						for (Line l : p.getLines())
						{
							if (CoordinatesType.LINE == type) {
								results.add(new BoxInfo(l.getId(),
									l.getBoundingBox().getBoundingBoxCoordinates()));
							} else {
								for (Word w : l.getWords())
								{
									results.add(new BoxInfo(w.getId(),
										w.getBoundingBox().getBoundingBoxCoordinates()));
								}
							}
						}
					}
				}
			}
		}
		return results;
	}
}
