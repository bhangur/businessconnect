package com.sravan.businessconnect.hateos.listener;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.sravan.businessconnect.hateos.event.SingleResourceRetrievedEvent;
import com.sravan.businessconnect.util.LinkUtil;

@Component
public class SingleResourceRetrievedDiscoverabilityListener implements ApplicationListener<SingleResourceRetrievedEvent>{

	@Override
	public void onApplicationEvent(SingleResourceRetrievedEvent resourceRetrievedEvent) {
		 Preconditions.checkNotNull(resourceRetrievedEvent);
		 
		 final HttpServletResponse response = resourceRetrievedEvent.getResponse();
		 
		 addLinkHeaderOnSingleResourceRetrieval(response);
		
	}

	private void addLinkHeaderOnSingleResourceRetrieval(final HttpServletResponse response) {
		
		final String requestURL = ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri().toASCIIString();
		final int positionOfLastSlash = requestURL.lastIndexOf("/");
        final String uriForResourceCreation = requestURL.substring(0, positionOfLastSlash);

        final String linkHeaderValue = LinkUtil.createLinkHeader(uriForResourceCreation, "collection");
        response.addHeader(HttpHeaders.LINK, linkHeaderValue);
	}

}
