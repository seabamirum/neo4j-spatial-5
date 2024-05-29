/*
 * Copyright (c) 2010-2020 "Neo4j,"
 * Neo4j Sweden AB [http://neo4j.com]
 *
 * This file is part of Neo4j Spatial.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.gis.spatial;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.io.WKTWriter;
import org.neo4j.gis.spatial.encoders.AbstractSinglePropertyEncoder;
import org.neo4j.gis.spatial.encoders.Configurable;
import org.neo4j.graphdb.Entity;
import org.neo4j.graphdb.Transaction;

public class WKTGeometryEncoder extends AbstractSinglePropertyEncoder implements Configurable {

	@Override
	public Geometry decodeGeometry(Entity container) {
		try {
			WKTReader reader = new WKTReader(layer.getGeometryFactory());
			return reader.read((String) container.getProperty(geomProperty));
		} catch (ParseException e) {
			throw new SpatialDatabaseException(e.getMessage(), e);
		}
	}

	@Override
	protected void encodeGeometryShape(Transaction tx, Geometry geometry, Entity container) {
		WKTWriter writer = new WKTWriter();
		container.setProperty(geomProperty, writer.write(geometry));
	}

	@Override
	public String getSignature() {
		return "WKT" + super.getSignature();
	}
}
