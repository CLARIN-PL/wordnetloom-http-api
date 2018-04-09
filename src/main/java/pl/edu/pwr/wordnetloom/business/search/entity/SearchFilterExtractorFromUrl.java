package pl.edu.pwr.wordnetloom.business.search.entity;

import javax.ws.rs.core.UriInfo;

public class SearchFilterExtractorFromUrl {

    private UriInfo uriInfo;
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PER_PAGE = 100;

    public SearchFilterExtractorFromUrl(final UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    public SearchFilter getFilter() {

        final SearchFilter searchFilter = new SearchFilter();

        searchFilter.setPaginationData(extractPaginationData());
        searchFilter.setLemma(getUriInfo().getQueryParameters().getFirst("lemma"));

        final String lexiconIdStr = getUriInfo().getQueryParameters().getFirst("lexiconId");
        if (lexiconIdStr != null) {
            searchFilter.setLexicon(Long.valueOf(lexiconIdStr));
        }

        final String posIdStr = getUriInfo().getQueryParameters().getFirst("partOfSpeechId");
        if (posIdStr != null) {
            searchFilter.setPartOfSpeechId(Long.valueOf(posIdStr));
        }

        final String domainIdStr = getUriInfo().getQueryParameters().getFirst("domainId");
        if (domainIdStr != null) {
            searchFilter.setDomainId(Long.valueOf(domainIdStr));
        }

        final String relationTypeId = getUriInfo().getQueryParameters().getFirst("relationTypeId");
        if (relationTypeId != null) {
            searchFilter.setRelationTypeId(Long.valueOf(relationTypeId));
        }

        final String statusId = getUriInfo().getQueryParameters().getFirst("statusId");
        if (statusId != null) {
            searchFilter.setStatusId(Long.valueOf(statusId));
        }

        final String registerId = getUriInfo().getQueryParameters().getFirst("registerId");
        if (registerId != null) {
            searchFilter.setRegisterId(Long.valueOf(registerId));
        }

        final String synsetIdStr = getUriInfo().getQueryParameters().getFirst("synsetId");
        if (synsetIdStr != null) {
            searchFilter.setSynsetId(Long.valueOf(synsetIdStr));
        }

        final String isAbstractStr = getUriInfo().getQueryParameters().getFirst("isAbstract");
        if(isAbstractStr != null){
            searchFilter.setAbstract(Boolean.valueOf(isAbstractStr));
        }

        final String isSynsetModeStr = getUriInfo().getQueryParameters().getFirst("synsetMode");
        if( isSynsetModeStr != null){
            searchFilter.setSynsetMode(Boolean.valueOf(isSynsetModeStr));
        } else{
            searchFilter.setSynsetMode(false);
        }


        searchFilter.setDefinition(getUriInfo().getQueryParameters().getFirst("definition"));

        searchFilter.setExample(getUriInfo().getQueryParameters().getFirst("example"));

        return searchFilter;
    }

    protected UriInfo getUriInfo() {
        return uriInfo;
    }

    protected PaginationData extractPaginationData() {
        final int perPage = getPerPage();
        final int firstResult = getPage() * perPage;

        return new PaginationData(firstResult, perPage);
    }

    private Integer getPage() {
        final String page = uriInfo.getQueryParameters().getFirst("page");
        if (page == null) {
            return DEFAULT_PAGE;
        }
        return Integer.parseInt(page);
    }

    private Integer getPerPage() {
        final String perPage = uriInfo.getQueryParameters().getFirst("per_page");
        if (perPage == null) {
            return DEFAULT_PER_PAGE;
        }
        return Integer.parseInt(perPage);
    }

}