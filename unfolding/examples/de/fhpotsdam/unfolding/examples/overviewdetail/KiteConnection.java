package de.fhpotsdam.unfolding.examples.overviewdetail;

import processing.core.PApplet;
import processing.core.PVector;

public class KiteConnection {

	private PApplet p;

	public float width = 150;
	public float height = 150;

	public float padding = 10;

	// Destination this connection points to (detail pos)
	public PVector dest = new PVector();
	// Position of the rectangle (zoom map)
	public PVector pos = new PVector();

	// Corners of the background rectangle
	protected PVector tl = new PVector();
	protected PVector tr = new PVector();
	protected PVector bl = new PVector();
	protected PVector br = new PVector();

	public KiteConnection(PApplet p) {
		this.p = p;
	}

	public void setPosition(float x, float y) {
		pos.x = x - width / 2;
		pos.y = y - height / 2;

		tl.x = pos.x - padding;
		tl.y = pos.y - padding;

		tr.x = pos.x + width + padding;
		tr.y = pos.y - padding;

		bl.x = pos.x - padding;
		bl.y = pos.y + height + padding;

		br.x = pos.x + width + padding;
		br.y = pos.y + height + padding;
	}

	public void setDestination(float x, float y) {
		dest.x = x;
		dest.y = y;
	}

	public void drawDebug() {
		p.fill(240);
		p.rect(pos.x, pos.y, width, height);

		p.fill(255, 0, 0, 100);
		p.ellipse(tr.x, tr.y, 10, 10);
		p.ellipse(tl.x, tl.y, 10, 10);
	}

	public void draw() {
		p.fill(100, 100);
		p.noStroke();
		p.beginShape();

		// Start point (only if dest not in center middle (5) )
		if (!(dest.y > tr.y && dest.y < br.y && dest.x >= tl.x && dest.x < tr.x)) {
			vertex(dest);
		}

		if (dest.y <= tr.y) {
			// 1: top left
			if (dest.x < tl.x) {
				vertex(tr);
				vertex(br);
				vertex(bl);
			}
			// 2: top middle
			if (dest.x >= tl.x && dest.x < tr.x) {
				vertex(tr);
				vertex(br);
				vertex(bl);
				vertex(tl);
			}
			// 3: top right
			if (dest.x >= tr.x) {
				vertex(br);
				vertex(bl);
				vertex(tl);
			}
		}

		if (dest.y > tr.y && dest.y < br.y) {
			// 4: center left
			if (dest.x < tl.x) {
				vertex(tl);
				vertex(tr);
				vertex(br);
				vertex(bl);
			}
			// 5: center middle
			if (dest.x >= tl.x && dest.x < tr.x) {
				vertex(tl);
				vertex(tr);
				vertex(br);
				vertex(bl);
			}
			// 6: center right
			if (dest.x > tr.x) {
				vertex(br);
				vertex(bl);
				vertex(tl);
				vertex(tr);
			}
		}

		if (dest.y >= br.y) {
			// 7: bottom left
			if (dest.x < tl.x) {
				vertex(br);
				vertex(tr);
				vertex(tl);
			}
			// 8: bottom middle
			if (dest.x >= tl.x && dest.x < tr.x) {
				vertex(br);
				vertex(tr);
				vertex(tl);
				vertex(bl);
			}
			// 9: bottom right
			if (dest.x >= tr.x) {
				vertex(tr);
				vertex(tl);
				vertex(bl);
			}
		}

		p.endShape(PApplet.CLOSE);
	}

	public void vertex(PVector v) {
		p.vertex(v.x, v.y);
	}
}