package org.olf.folio.order.util;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.marc4j.MarcJsonWriter;
import org.marc4j.marc.DataField;
import org.marc4j.marc.MarcFactory;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

public class MarcUtils {

    private final String TITLE_FIELD = "245";
    private final String TITLE_FIELD_TITLE1_SUBFIELD = "a";
    private final String TITLE_FIELD_TITLE2_SUBFIELD = "b";
    private final String TITLE_FIELD_TITLE3_SUBFIELD = "c";

    private final String PRICE_FIELD = "981";
    private final String PRICE_SUBFIELD = "i";
    private final String CURRENCY_FIELD = "981";
    private final String CURRENCY_SUBFIELD = "k";
    private final String RUSH_FIELD = "981";
    private final String RUSH_SUBFIELD = "q";

    private final String QUANTITY_FIELD = "980";
    private final String QUANTITY_SUBFIELD = "g";
    private final String FUNDCODE_FIELD = "980";
    private final String FUNDCODE_SUBFIELD = "h";
    private final String VENDOR_FIELD = "980";
    private final String VENDOR_SUBFIELD = "v";
    private final String VENDORID_FIELD = "961";
    private final String VENDORID_SUBFIELD = "i";

    private final String INTERNAL_NOTES_FIELD = "980";
    private final String INTERNAL_NOTES_SUBFIELD = "z";
    private final String RECEIVING_NOTE_FIELD = "981";
    private final String RECEIVING_NOTE_SUBFIELD = "n";

    private final String ELECTRONIC_FIELD = "980";
    private final String ELECTRONIC_SUBFIELD = "z";
    private final String SELECTOR_FIELD = "980";
    private final String SELECTOR_SUBFIELD = "y";

    private final String LOCATION_FIELD = "952";
    private final String LOCATION_SUBFIELD = "b";
    private final String REQUESTER_FIELD = "981";
    private final String REQUESTER_SUBFIELD = "r";

    private final String PUBLISHER_FIELD = "264";
    private final String PUBLISHER_SUBFIELD = "b";
    private final String PUBLISHER_LOCATION_FIELD = "264";
    private final String PUBLISHER_LOCATION_SUBFIELD = "a";
    private final String PUBLICATION_DATE_FIELD = "264";
    private final String PUBLICATION_DATE_SUBFIELD = "c";

    private final String BARCODE_FIELD = "976";
    private final String BARCODE_SUBFIELD = "p";
    private final String RECORD_SOURCE_FIELD = "948";
    private final String RECORD_SOURCE_SUBFIELD = "h";

    public MarcUtils() {
        // TODO Auto-generated constructor stub
    }

    public String getTitle(Record record) {
        String title = new String();
        DataField field = (DataField) record.getVariableField(TITLE_FIELD);
        if (field != null) {
            title = field.getSubfieldsAsString(TITLE_FIELD_TITLE1_SUBFIELD);
            String title2 =
                field.getSubfieldsAsString(TITLE_FIELD_TITLE2_SUBFIELD);
            String title3 =
                field.getSubfieldsAsString(TITLE_FIELD_TITLE3_SUBFIELD);
            if (title2 != null) title += " " + title2;
            if (title3 != null) title += " " + title3;
        }
        return title;
    }

    /**
     * getPrice from marc, defalt to 0.00.
     */
    public String getPrice(Record record) {
        String price = new String();
        DataField field = (DataField) record.getVariableField(PRICE_FIELD);
        if (field != null) {
            price = field.getSubfieldsAsString(PRICE_SUBFIELD);
            if (price == null) {
                price = "0.00";
            } else  {
                return normalizePrice(price);
            }
        } else {
            price = "0.00";
        }
        return price;
    }

    public String getQuantity(Record record) {
        DataField field = (DataField) record.getVariableField(QUANTITY_FIELD);
        if (field != null) {
            return field.getSubfieldsAsString(QUANTITY_SUBFIELD);
        } else {
            return new String();
        }
    }

    public String getFundCode(Record record) {
        DataField field = (DataField) record.getVariableField(FUNDCODE_FIELD);
        if (field != null) {
            return field.getSubfieldsAsString(FUNDCODE_SUBFIELD);
        } else {
            return new String();
        }
    }

    public String getVendorCode(Record record) {
        DataField field = (DataField) record.getVariableField(VENDOR_FIELD);
        if (field != null) {
            return field.getSubfieldsAsString(VENDOR_SUBFIELD);
        } else {
            return new String();
        }
    }

    public String getVendorItemId(Record record) {
        DataField field = (DataField) record.getVariableField(VENDORID_FIELD);
        if (field != null) {
            return field.getSubfieldsAsString(VENDORID_SUBFIELD);
        } else {
            return null;
        }
    }

    public String getLocation(Record record) {
        DataField field = (DataField) record.getVariableField(LOCATION_FIELD);
        if (field != null) {
            return field.getSubfieldsAsString(LOCATION_SUBFIELD);
        } else {
            return "olin";
        }
    }

    public String getRequester(Record record) {
        DataField field = (DataField) record.getVariableField(REQUESTER_FIELD);
        if (field != null) {
            return field.getSubfieldsAsString(REQUESTER_SUBFIELD);
        } else {
            return new String();
        }
    }

    public String getCurrency(Record record) {
        DataField field = (DataField) record.getVariableField(CURRENCY_FIELD);
        if (field != null) {
            return field.getSubfieldsAsString(CURRENCY_SUBFIELD);
        } else {
            return new String();
        }
    }

    public String getRush(Record record) {
        DataField field = (DataField) record.getVariableField(RUSH_FIELD);
        if (field != null) {
            return field.getSubfieldsAsString(RUSH_SUBFIELD);
        } else {
            return new String();
        }
    }

    public String getInternalNotes(Record record) {
        DataField field =
            (DataField) record.getVariableField(INTERNAL_NOTES_FIELD);
        if (field != null) {
            return field.getSubfieldsAsString(INTERNAL_NOTES_SUBFIELD);
        };
        return null;
    }

    public String getReceivingNote(Record record) {
        DataField field =
            (DataField) record.getVariableField(RECEIVING_NOTE_FIELD);
        if (field != null) {
            return field.getSubfieldsAsString(RECEIVING_NOTE_SUBFIELD);
        } else {
            return new String();
        }
    }

    public String getSelector(Record record) {
        DataField field = (DataField) record.getVariableField(SELECTOR_FIELD);
        if (field != null) {
            return field.getSubfieldsAsString(SELECTOR_SUBFIELD);
        } else {
            return new String();
        }
    }

    public String getElectronicIndicator(Record record) {
        DataField field = (DataField) record.getVariableField(ELECTRONIC_FIELD);
        if (field != null) {
            return field.getSubfieldsAsString(ELECTRONIC_SUBFIELD);
        }
        return new String();
    }

    public String getPublisher(Record record) {
        String publisher = new String();
        List<DataField> datafields = new ArrayList<DataField>();
        List<DataField> fields = record.getDataFields();

        for (DataField df: fields) {
            char id2 = df.getIndicator2();
            if (df.getTag().equals(PUBLISHER_FIELD) && id2 == '1' ) {
                datafields.add(df);
            }
        }
        if (datafields.size() > 0 ) {
            DataField twoSixtyFour = datafields.get(0);
            List<Subfield> subfields = twoSixtyFour.getSubfields(PUBLISHER_SUBFIELD);

            for (Subfield sf: subfields) {
                publisher += sf.getData() + " ";
            }
        } else {
            return null;
        }
        return StringUtils.removeEnd(publisher.trim(), ",").trim();
    }

    public String getPublicationDate(Record record) {
        DataField field = (DataField) record.getVariableField(PUBLICATION_DATE_FIELD);
        if (field != null) {
            return matchYear(field.getSubfieldsAsString(PUBLICATION_DATE_SUBFIELD));
        } else {
            return null;
        }
    }

    public String getBarcode(Record record) {
        DataField field = (DataField) record.getVariableField(BARCODE_FIELD);
        if (field != null) {
            return field.getSubfieldsAsString(BARCODE_SUBFIELD).trim();
        } else {
            return null;
        }
    }

    public String getRecordSource(Record record) {
        String recordSource = new String();
        List<DataField> fields = record.getDataFields();

        for (DataField df: fields) {
            // Need 948 0/ $h
            char id1 = df.getIndicator1();
            char id2 = df.getIndicator2();

            if (df.getTag().equals(RECORD_SOURCE_FIELD) && id1 == '0' && id2 == ' ' ) {
                recordSource = df.getSubfieldsAsString(RECORD_SOURCE_SUBFIELD);
                return recordSource;
            }
        }
        return recordSource;
    }

    public JSONArray getLinks(Record record) {
        JSONArray eResources = new JSONArray();
        List<VariableField> urls = record.getVariableFields("856");
        Iterator<VariableField> iterator = urls.iterator();
        String linkText = new String("Avaialble to Snapshot Users");
        while (iterator.hasNext()) {
            DataField dataField = (DataField) iterator.next();
            if (dataField != null && dataField.getSubfield('u') != null) {
                String url = dataField.getSubfield('u').getData();
                if (dataField.getSubfield('z') != null) {
                    linkText = dataField.getSubfield('z').getData();
                }
                JSONObject eResource = new JSONObject();
                eResource.put("uri", dataField.getSubfield('u').getData());
                //TODO - DO WE WANT TO CHANGE THE LINK TEXT?
                eResource.put("linkText", linkText);
                //I 'THINK' THESE RELATIONSHIP TYPES ARE HARDCODED INTO FOLIO
                //CANT BE LOOKED UP WITH AN API?
                //https://github.com/folio-org/mod-inventory-storage/blob/master/reference-data/electronic-access-relationships/resource.json
                eResource.put("relationshipId", "f5d0068e-6272-458e-8a81-b85e7b9a14aa");
                eResources.put(eResource);
            }
        }
        return eResources;


    }

    //TODO - FIX THESE METHODS THAT GATHER DETAILS FROM THE MARC RECORD.
    //THEY WERE HURRILY CODED
    //JUST WANTED TO GET SOME DATA IN THE INSTANCE
    //FROM THE MARC RECORD FOR THIS POC
    public JSONArray buildContributors(Record record, HashMap<String,String> lookupTable) {
        JSONArray contributors = new JSONArray();
        String[] subfields = {"a","b","c","d","f","g","j","k","l","n","p","t","u"};

        List<DataField> fields = record.getDataFields();
        Iterator<DataField> fieldsIterator = fields.iterator();
        while (fieldsIterator.hasNext()) {
            DataField field = (DataField) fieldsIterator.next();
            if (field.getTag().equalsIgnoreCase("100") || field.getTag().equalsIgnoreCase("700")) {
                contributors.put(makeContributor(field,lookupTable, "Personal name", subfields));
            }
        }
        return contributors;
    }

    public JSONObject makeContributor( DataField field, HashMap<String,String> lookupTable, String name_type_id, String[] subfieldArray) {
        List<String> list = Arrays.asList(subfieldArray);
        JSONObject contributor = new JSONObject();
        contributor.put("name", "");
        contributor.put("contributorNameTypeId", lookupTable.get(name_type_id));
        List<Subfield> subfields =  field.getSubfields();
        Iterator<Subfield> subfieldIterator = subfields.iterator();
        String contributorName = "";
        while (subfieldIterator.hasNext()) {
            Subfield subfield = (Subfield) subfieldIterator.next();
            String subfieldAsString = String.valueOf(subfield.getCode());
            if (subfield.getCode() == '4') {
                if (lookupTable.get(subfield.getData()) != null) {
                    contributor.put("contributorTypeId", lookupTable.get(subfield.getData()));
                }
                else {
                    contributor.put("contributorTypeId", lookupTable.get("bkp"));
                }
            }
            else if (subfield.getCode() == 'e') {
                contributor.put("contributorTypeText", subfield.getData());
            }
            else if (list.contains(subfieldAsString)) {
                if (!contributorName.isEmpty()) {
                    contributorName += ", " + subfield.getData();
                }
                else {
                    contributorName +=  subfield.getData();
                }
            }

        }
        contributor.put("name", contributorName);
        return contributor;
    }


   public JSONArray buildIdentifiers(Record record, HashMap<String, String> lookupTable) {
        JSONArray identifiers = new JSONArray();

        List<DataField> fields = record.getDataFields();
        Iterator<DataField> fieldsIterator = fields.iterator();
        while (fieldsIterator.hasNext()) {
            DataField field = (DataField) fieldsIterator.next();
            List<Subfield> subfields =  field.getSubfields();
            Iterator<Subfield> subfieldIterator = subfields.iterator();
            while (subfieldIterator.hasNext()) {
                Subfield subfield = (Subfield) subfieldIterator.next();
                if (field.getTag().equalsIgnoreCase("020")) {
                    if (subfield.getCode() == 'a') {
                        JSONObject identifier = new JSONObject();
                        String fullValue = subfield.getData();
                        if (field.getSubfield('c') != null) fullValue += " "  + field.getSubfieldsAsString("c");
                        if (field.getSubfield('q') != null) fullValue += " " + field.getSubfieldsAsString("q");
                        identifier.put("value",fullValue);

                        identifier.put("identifierTypeId", lookupTable.get("ISBN"));
                        identifiers.put(identifier);
                    }
                    if (subfield.getCode() == 'z') {
                        JSONObject identifier = new JSONObject();
                        String fullValue = subfield.getData();
                        if (field.getSubfield('c') != null) fullValue += " " + field.getSubfieldsAsString("c");
                        if (field.getSubfield('q') != null) fullValue += " " + field.getSubfieldsAsString("q");
                        identifier.put("value", fullValue);
                        identifier.put("identifierTypeId", lookupTable.get("Invalid ISBN"));
                        identifiers.put(identifier);
                    }
                }
                if (field.getTag().equalsIgnoreCase("022")) {
                    if (subfield.getCode() == 'a') {
                        JSONObject identifier = new JSONObject();
                        String fullValue = subfield.getData();
                        if (field.getSubfield('c') != null) fullValue += " " + field.getSubfieldsAsString("c");
                        if (field.getSubfield('q') != null) fullValue += " " + field.getSubfieldsAsString("q");
                        identifier.put("value",fullValue);

                        identifier.put("identifierTypeId", lookupTable.get("ISSN"));
                        identifiers.put(identifier);
                    } else if (subfield.getCode() == 'l') {
                        JSONObject identifier = new JSONObject();
                        String fullValue = subfield.getData();
                        if (field.getSubfield('c') != null) fullValue += " " + field.getSubfieldsAsString("c");
                        if (field.getSubfield('q') != null) fullValue += " " + field.getSubfieldsAsString("q");
                        identifier.put("value", fullValue);
                        identifier.put("identifierTypeId", lookupTable.get("Linking ISSN"));
                        identifiers.put(identifier);
                    } else {
                        JSONObject identifier = new JSONObject();
                        String fullValue = "";
                        if (field.getSubfield('z') != null) fullValue += field.getSubfieldsAsString("z");
                        if (field.getSubfield('y') != null) fullValue += " " +  field.getSubfieldsAsString("y");
                        if (field.getSubfield('m') != null) fullValue += " " + field.getSubfieldsAsString("m");
                        if (fullValue != "") {
                            identifier.put("value", fullValue);
                            identifier.put("identifierTypeId", lookupTable.get("Invalid ISSN"));
                            identifiers.put(identifier);
                        }
                    }
                }


            }

        }
        return identifiers;
    }

    public List<String> getSeriesFields(Record record) {
        List<DataField> fields = record.getDataFields();
        Iterator<DataField> fieldsIterator = fields.iterator();
        List<String> seriesFields = new ArrayList<String>();

        while (fieldsIterator.hasNext()) {

            DataField field = (DataField) fieldsIterator.next();
            if (StringUtils.equals(field.getTag(), "490") || StringUtils.equals(field.getTag(), "830") ) {
                seriesFields.add(field.toString());
            }
        }
        return seriesFields;
    }

    public String normalizePrice(String priceStr) {
        try {
           double f = Double.parseDouble(priceStr);
           return String.format("%.2f", new BigDecimal(f));
        } catch (NumberFormatException e) {
            return "0.00";
        }
    }

    public String matchYear(String pubDate) {
        String year = new String();
        try {
            Pattern pattern = Pattern.compile("(\\d{4})");
            Matcher matcher = pattern.matcher(pubDate);

            if (matcher.find()) {
               year = matcher.group(1);
            }
            return year;
        } catch (Exception e) {
            return "";
        }
    }


    public String recordToMarcJson(Record record) throws IOException {
      try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
        final MarcJsonWriter writer = new MarcJsonWriter(out);
        trimBarcode(record);
        writer.write(record);
        writer.close();
        return out.toString();
      }
    }

    private void trimBarcode(Record record) {
      DataField nineSevenSix = (DataField) record.getVariableField(BARCODE_FIELD);
      String barcode = getBarcode(record);

      // Trim spaces from barcode (976$p)
      if (barcode != null) {
        // Remove existing subfield
        Character barcodeCode = BARCODE_SUBFIELD.charAt(0);
        Subfield originalBarcode = nineSevenSix.getSubfield(barcodeCode);
        nineSevenSix.removeSubfield(originalBarcode);

        // Add new subfield with trimmed barcode
        MarcFactory factory = MarcFactory.newInstance();
        nineSevenSix.addSubfield(factory.newSubfield(barcodeCode, barcode));
      }
    }
}
