/*
 * This file is part of Sponge, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.common.data.builder.block.tileentity;

import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntity;
import org.spongepowered.api.block.entity.Sign;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.data.persistence.DataView;
import org.spongepowered.api.data.persistence.InvalidDataException;
import org.spongepowered.api.text.Text;
import org.spongepowered.common.text.SpongeTexts;
import org.spongepowered.common.util.Constants;

import java.util.List;
import java.util.Optional;

public class SpongeSignBuilder extends AbstractTileBuilder<Sign> {

    public SpongeSignBuilder() {
        super(Sign.class, 1);
    }

    @Override
    protected Optional<Sign> buildContent(DataView container) throws InvalidDataException {
        return super.buildContent(container).flatMap(sign -> {
            if (!container.contains(Constants.TileEntity.SIGN_LINES)) {
                return Optional.empty();
            }
            final SignTileEntity tileEntity = (SignTileEntity) sign;
            final List<String> rawLines = container.getStringList(Constants.TileEntity.SIGN_LINES).get();
            final List<Text> textLines = SpongeTexts.fromJson(rawLines);
            for (int i = 0; i < 4; i++) {
                tileEntity.signText[i] = SpongeTexts.toComponent(textLines.get(i));
            }
            tileEntity.validate();
            return Optional.of(sign);
        });
    }
}
