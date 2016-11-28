/*
 * Copyright 2016 Fritz Elfert
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jenkins.plugins.jclouds.config;

import hudson.Extension;
import org.jenkinsci.lib.configprovider.AbstractConfigProviderImpl;
import org.jenkinsci.lib.configprovider.model.Config;
import org.jenkinsci.lib.configprovider.model.ContentType;
import org.kohsuke.stapler.DataBoundConstructor;

public class UserDataBoothook extends Config {
    private static final long serialVersionUID = 1L;

    @DataBoundConstructor
    public UserDataBoothook(final String id, final String name, final String comment, final String content) {
        super(id, name, comment, content);
    }

    @Extension(ordinal = 70)
    public static class UserDataBoothookProvider extends AbstractConfigProviderImpl {

        private static final String SIGNATURE = "#cloud-boothook\n";
        private static final String DEFAULT_CONTENT = SIGNATURE;
        private static final String DEFAULT_NAME = "jclouds.boothook";

        public UserDataBoothookProvider() {
            load();
        }

        public String getSignature() {
            return SIGNATURE;
        }

        @Override
        public ContentType getContentType() {
            return CloudInitContentType.BOOTHOOK;
        }

        @Override
        public String getDisplayName() {
            return "JClouds user data (boot hook)";
        }

        @Override
        public UserDataBoothook getConfigById(final String configId) {
            final Config c = super.getConfigById(configId);
            return new UserDataBoothook(c.id, c.name, c.comment, c.content);
        }

        @Override
        public Config newConfig() {
            String id = getProviderId() + "." + System.currentTimeMillis();
            return new Config(id, DEFAULT_NAME, "", DEFAULT_CONTENT);
        }

        @Override
        public Config newConfig(final String id) {
            return new Config(id, DEFAULT_NAME, "", DEFAULT_CONTENT, getProviderId());
        }
    }

}