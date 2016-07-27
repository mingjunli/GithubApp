package com.anly.githubapp.data.net.request;

public class CreateAuthorization {
  /**
   * A list of scopes that this authorization is in.
   */
  public String[] scopes;

  /**
   * Required. A note to remind you what the OAuth token is for. Tokens not associated with a
   * specific OAuth application (i.e. personal access tokens) must have a unique note.
   */
  public String note;

  /**
   * A URL to remind you what app the OAuth token is for.
   */
  public String note_url;

  /**
   * The 20 character OAuth app client key for which to create the token.
   */
  public String client_id;

  /**
   * The 40 character OAuth app client secret for which to create the token.
   */
  public String client_secret;

  /**
   * A unique string to distinguish an authorization from others created for the same client ID and
   * user.
   */
  public String fingerprint;
}
